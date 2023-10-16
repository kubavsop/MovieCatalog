package com.example.moviescatalog.di

import com.example.domain.repositroy.UserAuthRepository
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideLoginUserUseCase(repository: UserAuthRepository): LoginUserUseCase {
        return LoginUserUseCase(repository = repository)
    }

    @Provides
    fun provideRegisterUserUseCase(repository: UserAuthRepository): RegisterUserUseCase {
        return RegisterUserUseCase(repository = repository)
    }
}