package com.example.moviescatalog.presentation.feature_film_screen

import com.example.domain.common.model.ModifiedMoviesDetails
import com.example.domain.common.model.ModifiedReview
import com.example.moviescatalog.presentation.util.UiText

private const val ZERO = 0
private const val EMPTY_STRING = ""
sealed interface FilmState {
    data object Loading : FilmState
    data object Initial : FilmState

    data object AuthorisationError: FilmState
    data class Content(
        val ageLimit: UiText,
        val budget: UiText,
        val haveReview: Boolean,
        val country: UiText,
        val description: String,
        val director: UiText,
        val fees: UiText,
        val genres: List<String>,
        val id: String,
        val name: String,
        val poster: String,
        val reviews: List<ModifiedReview>,
        val tagline: UiText,
        val time: UiText,
        val year: UiText,
        val inFavorite: Boolean,
        val averageRating: Double
    ) : FilmState

    data class ReviewDialogChanged(
        val isSaveActive: Boolean
    ) : FilmState

    data class ReviewDialog(
        val rating: Int = ZERO,
        val reviewText: String = EMPTY_STRING,
        val isAnonymous: Boolean = false
    ) : FilmState
}