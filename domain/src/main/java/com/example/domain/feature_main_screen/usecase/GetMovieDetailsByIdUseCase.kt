package com.example.domain.feature_main_screen.usecase

import com.example.domain.feature_favorite_screen.usecase.MovieInFavoriteUseCase
import com.example.domain.feature_main_screen.repository.MoviesRepository
import com.example.domain.common.usecase.GetUserIdUseCase
import com.example.domain.common.model.ModifiedMoviesDetails
import com.example.domain.common.model.ModifiedReview
import java.text.SimpleDateFormat

class GetMovieDetailsByIdUseCase(
    private val repository: MoviesRepository,
    private val movieInFavoriteUseCase: MovieInFavoriteUseCase,
    private val getUserIdUseCase: GetUserIdUseCase
) {
    suspend operator fun invoke(id: String): ModifiedMoviesDetails {
        val userId = getUserIdUseCase()?.id
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val moviesDetails = repository.getMovieDetailsById(id)
        var haveReview = false

        val reviews = moviesDetails.reviews.map { review ->
            if (userId == review.author?.userId) {
                haveReview = true
            }
            ModifiedReview(
                author = review.author,
                createDateTime = outputFormat.format(inputFormat.parse(review.createDateTime)),
                id = review.id,
                isAnonymous = review.isAnonymous,
                rating = review.rating,
                reviewText = review.reviewText,
                isMine = userId == review.author?.userId
            )
        }

        return with(moviesDetails) {
            ModifiedMoviesDetails(
                ageLimit = ageLimit,
                budget = budget,
                country = country,
                description = description,
                director = director,
                fees = fees,
                genres = genres,
                id = id,
                name = name,
                poster = poster,
                tagline = tagline,
                time = time,
                year = year,
                inFavorite = movieInFavoriteUseCase(id),
                averageRating = String.format("%.1f", reviews.map { it.rating }.average())
                    .toDouble(),
                reviews = reviews,
                haveReview = haveReview
            )
        }
    }

    private companion object {
        const val OUTPUT_FORMAT = "dd.MM.yyyy"
        const val INPUT_FORMAT = "yyyy-MM-dd"
    }
}