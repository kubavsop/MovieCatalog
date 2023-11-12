package com.example.moviescatalog.presentation.feature_film_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.feature_favorite_screen.usecase.AddFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.DeleteFavoriteMovieUseCase
import com.example.domain.feature_favorite_screen.usecase.MovieInFavoriteUseCase
import com.example.domain.feature_film_screen.usecase.AddMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.DeleteMovieReviewUseCase
import com.example.domain.feature_film_screen.usecase.EditMovieReviewUseCase
import com.example.domain.feature_main_screen.usecase.GetMovieDetailsByIdUseCase
import com.example.domain.feature_user_auth.usecase.GetUserIdUseCase
import com.example.domain.model.ModifiedMoviesDetails
import com.example.domain.model.MovieDetails
import com.example.domain.model.MovieElement
import com.example.domain.model.Review
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
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
    private val _state = MutableLiveData<FilmState>(FilmState.Initial)
    val state: LiveData<FilmState> = _state

    fun onEvent(event: FilmEvent) {
        when (event) {
            is FilmEvent.GetMovieDetails -> movieDetails(event.id)
            is FilmEvent.FavoriteChanged -> favoriteChanged(event.isAdd, event.id)
        }
    }

    private fun favoriteChanged(isAdd: Boolean, id: String) {
        try {
            viewModelScope.launch {
                if (isAdd) {
                    addFavoriteMovieUseCase(id)
                } else {
                    deleteFavoriteMovieUseCase(id)
                }
            }
        } catch (e: Exception) {
            throw e // TODO
        }
    }

    private fun movieDetails(id: String) {
        try {
            viewModelScope.launch {
                _state.value = FilmState.Loading
                val movieDetails = getMovieDetailsByIdUseCase(id)
                _state.value = FilmState.Content(movieDetails.toFilmItem())
            }
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            Unit // TODO
        }
    }

    private fun ModifiedMoviesDetails.toFilmItem(): List<FilmRecyclerViewItem> {
        return listOf(
            FilmRecyclerViewItem.HeaderItem(
                ageLimit = ageLimit,
                budget = budget,
                country = country,
                description = description,
                director = director,
                fees = fees,
                genres = genres,
                inFavorite = inFavorite,
                name = name,
                poster = poster,
                tagline = tagline,
                time = time,
                year = year,
                haveReview = haveReview,
                id = id,
                averageRating = averageRating
            )
        ) + reviews.map { review ->
            FilmRecyclerViewItem.ReviewItem(
                author = review.author,
                createDateTime = review.createDateTime,
                id = review.id,
                isAnonymous = review.isAnonymous,
                rating = review.rating,
                reviewText = review.reviewText,
                isMine = review.isMine
            )
        }
    }
}