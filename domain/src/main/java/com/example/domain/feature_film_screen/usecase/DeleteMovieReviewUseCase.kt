package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository

class DeleteMovieReviewUseCase (
    private val repository: FilmRepository
) {
    suspend operator fun invoke(
        movieId: String,
        id: String
    ) {
        repository.deleteMovieReview(movieId = movieId, id = id)
    }
}