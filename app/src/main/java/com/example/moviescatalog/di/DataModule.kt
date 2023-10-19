package com.example.moviescatalog.di

import com.example.data.remote.UserAuthApi
import com.example.data.repository.UserAuthRepositoryImpl
import com.example.data.validator.EmailPatternValidatorImpl
import com.example.domain.repositroy.UserAuthRepository
import com.example.domain.validator.EmailPatternValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideUserAuthRepository(userAuthApi: UserAuthApi): UserAuthRepository {
        return UserAuthRepositoryImpl(userAuthApi = userAuthApi)
    }

    @Singleton
    @Provides
    fun provideEmailPatternValidator(): EmailPatternValidator {
        return EmailPatternValidatorImpl()
    }
}