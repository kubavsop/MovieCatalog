package com.example.domain.feature_favorite_screen.repository

import com.example.domain.common.model.MovieElement

interface FavoriteRepository {

    suspend fun getFavoriteMovies(): List<MovieElement>

    suspend fun deleteFavoriteMovie(id: String)

    suspend fun addFavoriteMovie(id: String)
}