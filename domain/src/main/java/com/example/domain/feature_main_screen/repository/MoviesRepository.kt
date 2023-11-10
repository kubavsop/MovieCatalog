package com.example.domain.feature_main_screen.repository

import com.example.domain.model.MovieDetails
import com.example.domain.model.MoviesPagedList

interface MoviesRepository {
    suspend fun getMoviesByPage(page: Int): MoviesPagedList
    suspend fun getMovieDetailsById(id: String): MovieDetails
}