package com.example.domain.feature_main_screen.usecase

import com.example.domain.model.MovieDetails
import com.example.domain.feature_main_screen.repository.MoviesRepository

class GetMovieDetailsByIdUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(id: String): MovieDetails {
        return repository.getMovieDetailsById(id)
    }
}