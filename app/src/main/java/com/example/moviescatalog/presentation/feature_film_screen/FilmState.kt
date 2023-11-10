package com.example.moviescatalog.presentation.feature_film_screen

import com.example.domain.model.MovieDetails
import com.example.moviescatalog.presentation.feature_film_screen.recycler_view.FilmRecyclerViewItem

sealed interface FilmState {
    data object Loading: FilmState
    data object Initial: FilmState
    data class Content(val movieDetails: List<FilmRecyclerViewItem>): FilmState
}