package com.example.moviescatalog.di

import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_main_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.feature_main_screen.usecase.GetMoviesByPageUseCase
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.feature_user_auth.usecase.FormatDateUseCase
import com.example.domain.feature_user_auth.usecase.LoginUserUseCase
import com.example.domain.feature_user_auth.usecase.RegisterUserUseCase
import com.example.domain.feature_user_auth.usecase.ValidateEmailUseCase
import com.example.domain.feature_user_auth.usecase.ValidateFirstNameUseCase
import com.example.domain.feature_user_auth.usecase.ValidateLoginUseCase
import com.example.domain.feature_user_auth.usecase.ValidatePasswordUseCase
import com.example.domain.feature_user_auth.usecase.ValidateRepeatedPasswordUseCase
import com.example.domain.feature_user_auth.validator.EmailPatternValidator
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

    @Provides
    fun provideDateUseCase() = FormatDateUseCase()

    @Provides
    fun provideGetMoviesByPageUseCase(repository: MoviesRepository): GetMoviesByPageUseCase {
        return GetMoviesByPageUseCase(repository = repository)
    }

    @Provides
    fun provideGetMovieDetailsByIdUseCase(repository: MoviesRepository): GetMovieDetailsByIdUseCase {
        return GetMovieDetailsByIdUseCase(repository = repository)
    }
}