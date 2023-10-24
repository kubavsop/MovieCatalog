package com.example.domain.feature_main_screen.usecase

import com.example.domain.feature_main_screen.model.MoviesPagedList
import com.example.domain.feature_main_screen.repository.MoviesRepository

class GetMovieByPageUseCase(
    private val repository: MoviesRepository
) {
    suspend operator fun invoke(page: Int): MoviesPagedList {
        return repository.getMoviesByPage(page)
    }
}