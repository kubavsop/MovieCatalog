package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.model.ReviewModify

class AddMovieReviewUseCase(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(
        reviewModify: ReviewModify,
        movieId: String
    ) {
        repository.addMovieReview(reviewModify, movieId)
    }
}