package com.example.moviescatalog.presentation.feature_film_screen

interface FilmEvent {
    data class GetMovieDetails(val id: String) : FilmEvent
    data class FavoriteChanged(val isAdd: Boolean, val id: String) : FilmEvent

    data class RatingChanged(val rating: Int): FilmEvent

    data class ReviewTextChanged(val text: String): FilmEvent

    data class SaveReview(val reviewText: String, val isAnonymous: Boolean): FilmEvent

    data object OpenReviewDialog: FilmEvent
}