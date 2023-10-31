package com.example.data.feature_favorite_screen.repository

import com.example.data.feature_favorite_screen.remote.FavoriteMoviesApi
import com.example.data.mapper.toMovieElement
import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.model.MovieElement

class FavoriteRepositoryImpl(
    private val favoriteMoviesApi: FavoriteMoviesApi
): FavoriteRepository {
    override suspend fun getFavoriteMovies(): List<MovieElement> {
        return favoriteMoviesApi.getFavoriteMovies().movies.map { it.toMovieElement() }
    }

    override suspend fun deleteFavoriteMovie(id: String) {
        favoriteMoviesApi.deleteFavoriteMovie(id = id)
    }

    override suspend fun addFavoriteMovie(id: String) {
        favoriteMoviesApi.addFavoriteMovie(id = id)
    }
}