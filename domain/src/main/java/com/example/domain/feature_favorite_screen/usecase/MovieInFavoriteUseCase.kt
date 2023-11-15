package com.example.domain.feature_favorite_screen.usecase

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository

class MovieInFavoriteUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(id: String): Boolean {
        val movies = repository.getFavoriteMovies()
        movies.forEach { movieElement ->
            if (movieElement.id == id) return true
        }
        return false
    }
}