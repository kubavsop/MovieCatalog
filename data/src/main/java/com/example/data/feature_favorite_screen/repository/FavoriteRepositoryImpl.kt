package com.example.data.feature_favorite_screen.repository

import com.example.data.common.mapper.toMovieElement
import com.example.data.common.remote.MovieCatalogApi
import com.example.domain.feature_favorite_screen.repository.FavoriteRepository
import com.example.domain.common.model.MovieElement
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FavoriteRepositoryImpl(
    private val favoriteMoviesApi: MovieCatalogApi,
    private val ioDispatcher: CoroutineDispatcher
) : FavoriteRepository {
    override suspend fun getFavoriteMovies(): List<MovieElement> {
        return withContext(ioDispatcher) {
            favoriteMoviesApi.getFavoriteMovies().movies.map { it.toMovieElement() }
        }
    }

    override suspend fun deleteFavoriteMovie(id: String) {
        withContext(ioDispatcher) { favoriteMoviesApi.deleteFavoriteMovie(id = id) }
    }

    override suspend fun addFavoriteMovie(id: String) {
        withContext(ioDispatcher) { favoriteMoviesApi.addFavoriteMovie(id = id) }
    }
}