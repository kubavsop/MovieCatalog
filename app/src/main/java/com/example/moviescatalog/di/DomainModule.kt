package com.example.moviescatalog.di

import com.example.domain.repositroy.UserAuthRepository
import com.example.domain.usecase.LoginUserUseCase
import com.example.domain.usecase.RegisterUserUseCase
import com.example.domain.usecase.ValidateEmailUseCase
import com.example.domain.usecase.ValidateFirstNameUseCase
import com.example.domain.usecase.ValidateLoginUseCase
import com.example.domain.usecase.ValidatePasswordUseCase
import com.example.domain.usecase.ValidateRepeatedPasswordUseCase
import com.example.domain.validator.EmailPatternValidator
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

    @Provides
    fun provideValidateEmailUseCase(emailPatternValidatorImpl: EmailPatternValidator): ValidateEmailUseCase {
        return ValidateEmailUseCase(validator = emailPatternValidatorImpl)
    }

    @Provides
    fun provideValidatePasswordUseCase() = ValidatePasswordUseCase()

    @Provides
    fun provideValidateRepeatedPasswordUseCase() = ValidateRepeatedPasswordUseCase()

    @Provides
    fun provideValidateFirstNameUseCase() = ValidateFirstNameUseCase()

    @Provides
    fun provideValidateLoginUseCase() = ValidateLoginUseCase()
}