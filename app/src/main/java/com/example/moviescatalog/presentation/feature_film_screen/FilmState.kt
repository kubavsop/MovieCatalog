package com.example.moviescatalog.presentation.feature_film_screen

import com.example.domain.common.model.ModifiedMoviesDetails

private const val ZERO = 0
private const val EMPTY_STRING = ""
sealed interface FilmState {
    data object Loading : FilmState
    data object Initial : FilmState

    data object AuthorisationError: FilmState
    data class Content(val movieDetails: ModifiedMoviesDetails) : FilmState

    data class ReviewDialogChanged(
        val isSaveActive: Boolean
    ) : FilmState

    data class ReviewDialog(
        val rating: Int = ZERO,
        val reviewText: String = EMPTY_STRING,
        val isAnonymous: Boolean = false
    ) : FilmState
}