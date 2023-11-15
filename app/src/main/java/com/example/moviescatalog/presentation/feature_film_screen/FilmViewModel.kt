package com.example.moviescatalog.presentation.feature_film_screen

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_favorite_screen.usecase.AddFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.DeleteFavoriteMovieUseCase
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import com.example.domain.feature_main_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.common.model.ModifiedMoviesDetails
import com.example.moviescatalog.R
import com.example.moviescatalog.presentation.util.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class FilmViewModel @Inject constructor(
    private val addMovieReviewUseCase: AddMovieReviewUseCase,
    private val deleteMovieReviewUseCase: DeleteMovieReviewUseCase,
    private val editMovieReviewUseCase: EditMovieReviewUseCase,
    private val getMovieDetailsByIdUseCase: GetMovieDetailsByIdUseCase,
    private val addFavoriteMovieUseCase: AddFavoriteMovieUseCase,
    private val deleteFavoriteMovieUseCase: DeleteFavoriteMovieUseCase,
) : ViewModel() {
    private lateinit var movieDetails: FilmState.Content

    private var currentReviewState = FilmState.ReviewDialog()
    private var reviewState = FilmState.ReviewDialog()

    private val _state = MutableLiveData<FilmState>(FilmState.Initial)
    val state: LiveData<FilmState> = _state


    fun onEvent(event: FilmEvent) {
        when (event) {
            is FilmEvent.GetMovieDetails -> movieDetails(event.id)
            is FilmEvent.FavoriteChanged -> favoriteChanged(event.isAdd)
            is FilmEvent.OpenReviewDialog -> reviewDialog(
                event.rating,
                event.reviewText,
                event.isAnonymous
            )

            is FilmEvent.SaveReview -> saveReview(event.id)

            is FilmEvent.DeleteReview -> deleteReview(event.id)

            is FilmEvent.RatingChanged -> ratingChanged(event.rating)
            is FilmEvent.ReviewTextChanged -> reviewTextChanged(event.text)
            is FilmEvent.IsAnonymousChanged -> isAnonymousChanged(event.flag)
        }
    }

    private fun deleteReview(id: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmState.Loading
                deleteMovieReviewUseCase(id = id, movieId = movieDetails.id)
                movieDetails(movieDetails.id)
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = FilmState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun saveReview(id: String?) {
        viewModelScope.launch {
            try {
                _state.value = FilmState.Loading
                if (id == null) {
                    addMovieReviewUseCase(
                        movieId = movieDetails.id,
                        isAnonymous = currentReviewState.isAnonymous,
                        reviewText = currentReviewState.reviewText,
                        rating = currentReviewState.rating
                    )
                } else {
                    editMovieReviewUseCase(
                        isAnonymous = if (currentReviewState.isAnonymous && !reviewState.isAnonymous) false else currentReviewState.isAnonymous,
                        rating = currentReviewState.rating,
                        reviewText = currentReviewState.reviewText,
                        movieId = movieDetails.id,
                        id = id
                    )
                }

                currentReviewState = FilmState.ReviewDialog()
                reviewState = FilmState.ReviewDialog()

                movieDetails(movieDetails.id)
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = FilmState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun reviewTextChanged(text: String) {
        currentReviewState = currentReviewState.copy(reviewText = text)
        isSaveActive()
    }

    private fun isAnonymousChanged(flag: Boolean) {
        currentReviewState = currentReviewState.copy(isAnonymous = flag)
        isSaveActive()
    }

    private fun ratingChanged(rating: Int) {
        currentReviewState = currentReviewState.copy(rating = rating)
        isSaveActive()
    }

    private fun reviewDialog(
        rating: Int,
        reviewText: String,
        isAnonymous: Boolean
    ) {
        reviewState = FilmState.ReviewDialog(
            rating = rating,
            reviewText = reviewText,
            isAnonymous = isAnonymous
        )
        currentReviewState = FilmState.ReviewDialog(
            rating = rating,
            reviewText = reviewText,
            isAnonymous = isAnonymous
        )

        _state.value = reviewState
    }

    private fun favoriteChanged(isAdd: Boolean) {
        viewModelScope.launch {
            try {

                if (isAdd) {
                    addFavoriteMovieUseCase(movieDetails.id)
                } else {
                    deleteFavoriteMovieUseCase(movieDetails.id)
                }

                movieDetails = movieDetails.copy(
                    inFavorite = isAdd
                )

                _state.value = movieDetails
            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = FilmState.AuthorisationError
                } else {
                    Unit
                }
            }
        }
    }

    private fun setString(@StringRes resId: Int, value: String): UiText {
        return if (value == "-") {
            UiText.DynamicString(value)
        } else {
            UiText.StringResource(resId, value)
        }
    }


    private fun movieDetails(id: String) {
        viewModelScope.launch {
            try {
                _state.value = FilmState.Loading
                movieDetails = getMovieDetailsByIdUseCase(id).toFilmState()
                _state.value = movieDetails

            } catch (e: HttpException) {
                if (e.code() == UNAUTHORIZED) {
                    _state.value = FilmState.AuthorisationError
                } else {
                    throw e
                }
            }
        }
    }

    private fun isSaveActive() {
        val comparisons = listOf(
            currentReviewState.rating == reviewState.rating,
            currentReviewState.isAnonymous == reviewState.isAnonymous,
            currentReviewState.reviewText == reviewState.reviewText,
        ).any { !it }
        _state.value =
            FilmState.ReviewDialogChanged(isSaveActive = comparisons && currentReviewState.rating != ZERO && currentReviewState.reviewText.isNotBlank())
    }

    private fun ModifiedMoviesDetails.toFilmState() = FilmState.Content(
        ageLimit = UiText.StringResource(R.string.age_limit, ageLimit),
        budget = UiText.StringResource(R.string.money, budget),
        haveReview = haveReview,
        country = UiText.DynamicString(country),
        description = description,
        director = UiText.DynamicString(director),
        fees = UiText.StringResource(R.string.money, fees),
        genres = genres,
        id = id,
        name = name,
        poster = poster,
        reviews = reviews,
        tagline = setString(R.string.tagline_ui, tagline),
        time = UiText.StringResource(R.string.film_time, time),
        year = UiText.DynamicString(year.toString()),
        inFavorite = inFavorite,
        averageRating = averageRating
    )

    private companion object {
        const val ZERO = 0
        const val UNAUTHORIZED = 401
    }
}