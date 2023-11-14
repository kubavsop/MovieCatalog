package com.example.domain.feature_main_screen.usecase

import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.common.usecase.GetUserIdUseCase

class GetRatingByMovieIdUseCase(
    private val repository: MoviesRepository,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(id: String): Int? {
        val reviews = repository.getMovieDetailsById(id.toString()).reviews
        var userRating: Int? = null

        for (review in reviews) {
            if (review.author == null) continue
            if (review.author.userId == getUserIdUseCase()
                    ?.let { it.id }
            ) {
                userRating = review.rating
                break
            }
        }

        return userRating
    }
}