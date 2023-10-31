package com.example.moviescatalog.di

import com.example.data.interceptor.AuthInterceptor
import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.data.feature_main_screen.local.MovieDatabase
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.feature_main_screen.remote.MovieRemoteMediator
import com.example.data.feature_main_screen.remote.MoviesApi
import com.example.data.feature_main_screen.repository.MoviesRepositoryImpl
import com.example.data.feature_profile_screen.remote.ProfileApi
import com.example.data.feature_profile_screen.repository.ProfileRepositoryImpl
import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.feature_user_auth.local.UserStorageImpl
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.data.feature_user_auth.repository.UserAuthRepositoryImpl
import com.example.data.feature_user_auth.validator.EmailPatternValidatorImpl
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.feature_user_auth.usecase.GetTokenUseCase
import com.example.domain.feature_user_auth.validator.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideProfileApi(authInterceptor: AuthInterceptor): ProfileApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(ProfileApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(ProfileApi::class.java)
    }

    @Singleton
    @Provides
    fun provideUserAuthApi(): UserAuthApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(UserAuthApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(UserAuthApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesApi(): MoviesApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(MoviesApi.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MoviesApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCharacterDatabase(@ApplicationContext context: Context): MovieDatabase {
        return Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            MovieDatabase.NAME
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCharacterPager(
        movieDatabase: MovieDatabase,
        moviesApi: MoviesApi
    ): Pager<Int, MovieElementEntity> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = MovieRemoteMediator(
                movieDatabase = movieDatabase,
                moviesApi = moviesApi
            ),
            pagingSourceFactory = {
                movieDatabase.dao.pagingSource()
            }
        )
    }

    @Singleton
    @Provides
    fun provideUserStorage(@ApplicationContext context: Context): UserStorage {
        return UserStorageImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideEmailPatternValidator(): EmailPatternValidator {
        return EmailPatternValidatorImpl()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(getTokenUseCase: GetTokenUseCase): AuthInterceptor {
        return AuthInterceptor(getTokenUseCase = getTokenUseCase)
    }

    @Singleton
    @Provides
    fun getTokenUseCase(userAuthRepository: UserAuthRepository): GetTokenUseCase {
        return GetTokenUseCase(repository = userAuthRepository)
    }


    @Singleton
    @Provides
    fun provideMovieRepository(moviesApi: MoviesApi): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi)
    }

    @Singleton
    @Provides
    fun provideUserAuthRepository(
        userAuthApi: UserAuthApi,
        userStorage: UserStorage
    ): UserAuthRepository {
        return UserAuthRepositoryImpl(userAuthApi = userAuthApi, userStorage = userStorage)
    }

    @Singleton
    @Provides
    fun provideProfileRepository(profileApi: ProfileApi): ProfileRepository {
        return ProfileRepositoryImpl(profileApi)
    }
}