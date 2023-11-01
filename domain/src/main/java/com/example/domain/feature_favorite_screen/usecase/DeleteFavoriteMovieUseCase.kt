package com.example.domain.feature_favorite_screen.usecase

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository

class DeleteFavoriteMovieUseCase (
    private val repository: FavoriteRepository
) {
    suspend operator fun invoke(id: String) {
        repository.deleteFavoriteMovie(id = id)
    }
}