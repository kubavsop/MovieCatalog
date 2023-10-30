package com.example.moviescatalog.di

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
import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.feature_user_auth.local.UserStorageImpl
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.data.feature_user_auth.repository.UserAuthRepositoryImpl
import com.example.data.feature_user_auth.validator.EmailPatternValidatorImpl
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.feature_user_auth.validator.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

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
            "movie.db"
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
    fun provideMovieRepository(moviesApi: MoviesApi): MoviesRepository {
        return MoviesRepositoryImpl(moviesApi)
    }


    @Singleton
    @Provides
    fun provideUserStorage(@ApplicationContext context: Context): UserStorage {
        return UserStorageImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideUserAuthRepository(userAuthApi: UserAuthApi, userStorage: UserStorage): UserAuthRepository {
        return UserAuthRepositoryImpl(userAuthApi = userAuthApi, userStorage = userStorage)
    }

    @Singleton
    @Provides
    fun provideEmailPatternValidator(): EmailPatternValidator {
        return EmailPatternValidatorImpl()
    }
}