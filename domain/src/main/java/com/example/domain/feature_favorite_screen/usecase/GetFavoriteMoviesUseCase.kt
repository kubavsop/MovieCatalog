package com.example.domain.feature_favorite_screen.usecase

import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.feature_main_screen.usecase.GetRatingByMovieIdUseCase
import com.example.domain.model.MovieElement

class GetFavoriteMoviesUseCase(
    private val repository: FavoriteRepository,
    private val getRatingByMovieIdUseCase: GetRatingByMovieIdUseCase
) {
    suspend operator fun invoke(): List<MovieElement> {
        return repository.getFavoriteMovies().map { movieElement ->
            movieElement.copy(userRating = getRatingByMovieIdUseCase(id = movieElement.id))
        }
    }
}