package com.example.moviescatalog.di

import android.content.Context
import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.feature_user_auth.local.UserStorageImpl
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.data.feature_user_auth.repository.UserAuthRepositoryImpl
import com.example.data.feature_user_auth.validator.EmailPatternValidatorImpl
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