package com.example.moviescatalog.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.data.common.interceptor.AuthInterceptor
import com.example.data.common.remote.MovieCatalogApi
import com.example.data.common.repository.UserRepositoryImpl
import com.example.data.feature_favorite_screen.repository.FavoriteRepositoryImpl
import com.example.data.feature_film_screen.repository.FilmRepositoryImpl
import com.example.data.feature_main_screen.local.MovieDatabase
import com.example.data.feature_main_screen.local.entity.MovieElementEntity
import com.example.data.feature_main_screen.remote.MovieRemoteMediator
import com.example.data.feature_main_screen.repository.MoviesRepositoryImpl
import com.example.data.feature_profile_screen.repository.ProfileRepositoryImpl
import com.example.data.common.local.UserStorage
import com.example.data.common.local.UserStorageImpl
import com.example.data.feature_user_auth.repository.UserAuthRepositoryImpl
import com.example.data.feature_user_auth.validator.EmailPatternValidatorImpl
import com.example.domain.common.repository.UserRepository
import com.example.domain.common.usecase.GetTokenUseCase
import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.feature_user_auth.validator.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Singleton
    @Provides
    fun provideMovieCatalogApi(authInterceptor: AuthInterceptor): MovieCatalogApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(MovieCatalogApi.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(authInterceptor)
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MovieCatalogApi::class.java)
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
    @Singleton
    @Provides
    fun provideCharacterPager(
        movieDatabase: MovieDatabase,
        moviesApi: MovieCatalogApi,
        userStorage: UserStorage
    ): Pager<Int, MovieElementEntity> {
        return Pager(
            config = PagingConfig(pageSize = 6),
            remoteMediator = MovieRemoteMediator(
                movieDatabase = movieDatabase,
                moviesApi = moviesApi,
                userStorage = userStorage
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

    @Provides
    fun provideGetTokenUseCase(userRepository: UserRepository): GetTokenUseCase {
        return GetTokenUseCase(repository = userRepository)
    }

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repository = profileRepository)
    }

    @Singleton
    @Provides
    fun provideUserRepository(userStorage: UserStorage): UserRepository{
        return UserRepositoryImpl(userStorage)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        moviesApi: MovieCatalogApi,
        ioDispatcher: CoroutineDispatcher
    ): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi, ioDispatcher = ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideUserAuthRepository(
        userAuthApi: MovieCatalogApi,
        userStorage: UserStorage,
        ioDispatcher: CoroutineDispatcher
    ): UserAuthRepository {
        return UserAuthRepositoryImpl(
            userAuthApi = userAuthApi,
            ioDispatcher = ioDispatcher
        )
    }

    @Provides
    @Singleton
    fun provideFavoriteRepository(
        favoriteMoviesApi: MovieCatalogApi,
        ioDispatcher: CoroutineDispatcher
    ): FavoriteRepository {
        return FavoriteRepositoryImpl(
            favoriteMoviesApi = favoriteMoviesApi,
            ioDispatcher = ioDispatcher
        )
    }

    @Singleton
    @Provides
    fun provideProfileRepository(
        profileApi: MovieCatalogApi,
        ioDispatcher: CoroutineDispatcher
    ): ProfileRepository {
        return ProfileRepositoryImpl(profileApi, ioDispatcher = ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideFilmRepository(
        filmApi: MovieCatalogApi,
        ioDispatcher: CoroutineDispatcher
    ): FilmRepository {
        return FilmRepositoryImpl(reviewApi = filmApi, ioDispatcher = ioDispatcher)
    }

    @Singleton
    @Provides
    fun provideIODispatcher() = Dispatchers.IO
}