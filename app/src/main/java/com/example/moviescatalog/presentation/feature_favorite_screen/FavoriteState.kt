package com.example.moviescatalog.presentation.feature_favorite_screen

import com.example.domain.common.model.MovieElement


sealed interface FavoriteState {
    data object Loading: FavoriteState
    data object Initial: FavoriteState
    data object Empty: FavoriteState
    data class Content(val movies: List<MovieElement>): FavoriteState
    data object AuthorisationError: FavoriteState
}