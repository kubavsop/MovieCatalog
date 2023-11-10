package com.example.moviescatalog.presentation.feature_film_screen

import com.example.domain.model.MovieDetails

sealed interface FilmState {
    data object Loading: FilmState
    data object Initial: FilmState
    data class Content(val movieDetails: MovieDetails): FilmState
}