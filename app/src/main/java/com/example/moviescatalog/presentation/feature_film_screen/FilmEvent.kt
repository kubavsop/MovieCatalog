package com.example.moviescatalog.presentation.feature_film_screen

private const val EMPTY_STRING = ""
private const val ZERO = 0

sealed interface FilmEvent {
    data class GetMovieDetails(val id: String) : FilmEvent
    data class FavoriteChanged(val isAdd: Boolean) : FilmEvent
    data class RatingChanged(val rating: Int) : FilmEvent
    data class ReviewTextChanged(val text: String) : FilmEvent
    data class IsAnonymousChanged(val flag: Boolean): FilmEvent
    data class SaveReview(val id: String? = null) : FilmEvent
    data class DeleteReview(val id: String): FilmEvent
    data class OpenReviewDialog(
        val rating: Int = ZERO,
        val reviewText: String = EMPTY_STRING,
        val isAnonymous: Boolean = false
    ) : FilmEvent
}