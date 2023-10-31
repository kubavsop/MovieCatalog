package com.example.domain.feature_favorite_screen.usecase

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository

class AddFavoriteMovieUseCase(
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(id: String) {
        repository.addFavoriteMovie(id = id)
    }
}