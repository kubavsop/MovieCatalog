package com.example.data.feature_main_screen.repository

import com.example.data.common.mapper.toMovieDetails
import com.example.data.common.mapper.toMoviesPagedList
import com.example.data.feature_main_screen.remote.MoviesApi
import com.example.domain.model.MovieDetails
import com.example.domain.model.MoviesPagedList
import com.example.domain.feature_main_screen.repository.MoviesRepository

class MoviesRepositoryImpl(
    private val moviesApi: MoviesApi
): MoviesRepository {
    override suspend fun getMoviesByPage(page: Int): MoviesPagedList {
        return moviesApi.getMoviesByPage(page = page).toMoviesPagedList()
    }

    override suspend fun getMovieDetailsById(id: String): MovieDetails {
        return moviesApi.getMovieDetailsById(id = id).toMovieDetails()
    }
}