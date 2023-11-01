package com.example.domain.feature_favorite_screen.usecase

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.model.MovieElement

class GetFavoriteMoviesUseCase (
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(): List<MovieElement> {
        return repository.getFavoriteMovies()
    }
}