package com.example.moviescatalog.di

import com.example.domain.common.repository.UserRepository
import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.feature_favorite_screen.usecase.AddFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.DeleteFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.GetFavoriteMoviesUseCase
import com.example.domain.feature_favorite_screen.usecase.MovieInFavoriteUseCase
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.feature_main_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.feature_main_screen.usecase.GetMoviesByPageUseCase
import com.example.domain.feature_main_screen.usecase.GetRatingByMovieIdUseCase
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import com.example.domain.feature_profile_screen.usecase.ChangeProfileUseCase
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_profile_screen.usecase.LogoutUseCase
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.feature_user_auth.usecase.FormatDateUseCase
import com.example.domain.common.usecase.GetUserIdUseCase
import com.example.domain.common.usecase.RemoveTokenUseCase
import com.example.domain.common.usecase.RemoveUserUseCase
import com.example.domain.common.usecase.SaveTokenUseCase
import com.example.domain.common.usecase.SaveUserUseCase
import com.example.domain.feature_profile_screen.usecase.ValidateUrlUseCase
import com.example.domain.feature_profile_screen.validator.UrlValidator
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
    fun provideLoginUserUseCase(
        repository: UserAuthRepository,
        getProfileUseCase: GetProfileUseCase,
        saveUserUseCase: SaveUserUseCase,
        saveTokenUseCase: SaveTokenUseCase
    ): LoginUserUseCase {
        return LoginUserUseCase(
            repository = repository,
            getProfileUseCase = getProfileUseCase,
            saveUserUseCase = saveUserUseCase,
            saveTokenUseCase = saveTokenUseCase
        )
    }

    @Provides
    fun provideRegisterUserUseCase(
        repository: UserAuthRepository,
        getProfileUseCase: GetProfileUseCase,
        saveTokenUseCase: SaveTokenUseCase,
        saveUserUseCase: SaveUserUseCase
    ): RegisterUserUseCase {
        return RegisterUserUseCase(
            repository = repository,
            getProfileUseCase = getProfileUseCase,
            saveTokenUseCase = saveTokenUseCase,
            saveUserUseCase = saveUserUseCase
        )
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
    fun provideGetMovieDetailsByIdUseCase(
        repository: MoviesRepository,
        movieInFavoriteUseCase: MovieInFavoriteUseCase,
        getUserIdUseCase: GetUserIdUseCase
    ): GetMovieDetailsByIdUseCase {
        return GetMovieDetailsByIdUseCase(
            repository = repository,
            getUserIdUseCase = getUserIdUseCase,
            movieInFavoriteUseCase = movieInFavoriteUseCase
        )
    }

    @Provides
    fun provideChangeProfileUseCase(profileRepository: ProfileRepository): ChangeProfileUseCase {
        return ChangeProfileUseCase(repository = profileRepository)
    }

    @Provides
    fun provideLogoutUseCase(
        profileRepository: ProfileRepository,
        removeTokenUseCase: RemoveTokenUseCase,
        removeUserUseCase: RemoveUserUseCase
    ): LogoutUseCase {
        return LogoutUseCase(
            repository = profileRepository,
            removeUserUseCase = removeUserUseCase,
            removeTokenUseCase = removeTokenUseCase
        )
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
    fun provideGetFavoriteMoviesUseCase(
        repository: FavoriteRepository,
        getRatingByMovieIdUseCase: GetRatingByMovieIdUseCase
    ): GetFavoriteMoviesUseCase {
        return GetFavoriteMoviesUseCase(
            repository = repository,
            getRatingByMovieIdUseCase = getRatingByMovieIdUseCase
        )
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

    @Provides
    fun provideGetUserIdUseCase(repository: UserRepository): GetUserIdUseCase {
        return GetUserIdUseCase(repository = repository)
    }

    @Provides
    fun provideMovieInFavoriteUseCase(repository: FavoriteRepository): MovieInFavoriteUseCase {
        return MovieInFavoriteUseCase(repository = repository)
    }

    @Provides
    fun provideSaveTokenUseCase(repository: UserRepository): SaveTokenUseCase {
        return SaveTokenUseCase(repository = repository)
    }

    @Provides
    fun provideSaveUserUseCase(repository: UserRepository): SaveUserUseCase {
        return SaveUserUseCase(repository = repository)
    }

    @Provides
    fun provideRemoveTokenUseCase(repository: UserRepository): RemoveTokenUseCase {
        return RemoveTokenUseCase(repository = repository)
    }

    @Provides
    fun provideRemoveUserUseCase(repository: UserRepository): RemoveUserUseCase {
        return RemoveUserUseCase(repository = repository)
    }

    @Provides
    fun provideValidateUrlUseCase(validator: UrlValidator): ValidateUrlUseCase {
        return ValidateUrlUseCase(validator = validator)
    }

    @Provides
    fun provideGetRatingByMovieIdUseCase(
        repository: MoviesRepository,
        getUserIdUseCase: GetUserIdUseCase
    ): GetRatingByMovieIdUseCase {
        return GetRatingByMovieIdUseCase(
            repository = repository,
            getUserIdUseCase = getUserIdUseCase
        )
    }
}