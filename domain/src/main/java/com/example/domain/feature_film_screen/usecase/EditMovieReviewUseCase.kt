package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.model.ReviewModify

class EditMovieReviewUseCase(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(
        reviewModify: ReviewModify, movieId: String, id: String
    ) {
        repository.editMovieReview(reviewModify = reviewModify, movieId = movieId, id = id)
    }
}