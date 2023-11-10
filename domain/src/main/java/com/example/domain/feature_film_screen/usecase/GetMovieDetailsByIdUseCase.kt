package com.example.domain.feature_film_screen.usecase

import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.model.MovieDetails
import com.example.domain.feature_main_screen.repository.MoviesRepository

class GetMovieDetailsByIdUseCase(
    private val repository: FilmRepository
) {
    suspend operator fun invoke(id: String): MovieDetails {
        return repository.getMovieDetailsById(id)
    }
}