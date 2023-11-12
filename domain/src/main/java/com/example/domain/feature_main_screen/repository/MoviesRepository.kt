package com.example.domain.feature_main_screen.repository

import com.example.domain.model.MovieDetails
import com.example.domain.model.MoviesPagedList
import java.util.UUID

interface MoviesRepository {
    suspend fun getMoviesByPage(page: Int): MoviesPagedList
    suspend fun getMovieDetailsById(id: String): MovieDetails
}