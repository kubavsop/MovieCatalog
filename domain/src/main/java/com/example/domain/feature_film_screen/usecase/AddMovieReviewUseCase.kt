package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.common.model.ReviewModify

class AddMovieReviewUseCase(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(
        isAnonymous: Boolean,
        rating: Int,
        reviewText: String,
        movieId: String
    ) {
        repository.addMovieReview(
            ReviewModify(
                isAnonymous = isAnonymous,
                rating = rating,
                reviewText = reviewText
            ), movieId
        )
    }
}