package com.example.moviescatalog.di

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.feature_favorite_screen.usecase.AddFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.DeleteFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.GetFavoriteMoviesUseCase
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_film_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.feature_main_screen.usecase.GetMoviesByPageUseCase
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import com.example.domain.feature_profile_screen.usecase.ChangeProfileUseCase
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_profile_screen.usecase.LogoutUseCase
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
    fun provideGetMovieDetailsByIdUseCase(repository: FilmRepository): GetMovieDetailsByIdUseCase {
        return GetMovieDetailsByIdUseCase(repository = repository)
    }

    @Provides
    fun provideGetProfileUseCase(profileRepository: ProfileRepository): GetProfileUseCase {
        return GetProfileUseCase(repository = profileRepository)
    }

    @Provides
    fun provideChangeProfileUseCase(profileRepository: ProfileRepository): ChangeProfileUseCase {
        return ChangeProfileUseCase(repository = profileRepository)
    }
    @Provides
    fun provideLogoutUseCase(profileRepository: ProfileRepository): LogoutUseCase {
        return LogoutUseCase(repository = profileRepository)
    }

    @Provides
    fun provideAddFavoriteMovieUseCase(repository: FavoriteRepository): AddFavoriteMovieUseCase {
        return AddFavoriteMovieUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteFavoriteMovieUseCase(repository: FavoriteRepository): DeleteFavoriteMovieUseCase {
        return DeleteFavoriteMovieUseCase(repository = repository)
    }

    @Provides
    fun provideGetFavoriteMoviesUseCase(repository: FavoriteRepository): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(repository = repository)
    }

    @Provides
    fun provideAddMovieReviewUseCase(repository: FilmRepository): AddMovieReviewUseCase {
        return AddMovieReviewUseCase(repository = repository)
    }

    @Provides
    fun provideDeleteMovieReviewUseCase(repository: FilmRepository): DeleteMovieReviewUseCase {
        return DeleteMovieReviewUseCase(repository = repository)
    }

    @Provides
    fun provideEditMovieReviewUseCase(repository: FilmRepository): EditMovieReviewUseCase {
        return EditMovieReviewUseCase(repository = repository)
    }
}