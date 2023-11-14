package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.common.model.ReviewModify

class EditMovieReviewUseCase(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(
        isAnonymous: Boolean,
        rating: Int,
        reviewText: String,
        movieId: String,
        id: String
    ) {
        repository.editMovieReview(
            reviewModify = ReviewModify(
                isAnonymous = isAnonymous,
                rating = rating,
                reviewText = reviewText
            ), movieId = movieId, id = id
        )
    }
}