package com.example.moviescatalog.presentation.feature_film_screen

interface FilmEvent {
    data class GetMovieDetails(val id: String) : FilmEvent
    data class FavoriteChanged(val isAdd: Boolean, val id: String) : FilmEvent
}