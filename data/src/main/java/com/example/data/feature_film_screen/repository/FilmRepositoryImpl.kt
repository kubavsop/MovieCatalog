package com.example.data.feature_film_screen.repository

import com.example.data.common.mapper.toReviewModifyDto
import com.example.data.common.remote.MovieCatalogApi
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.common.model.ReviewModify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilmRepositoryImpl(
    private val reviewApi: MovieCatalogApi,
    private val ioDispatcher: CoroutineDispatcher
) : FilmRepository {
    override suspend fun addMovieReview(reviewModify: ReviewModify, movieId: String) {
        withContext(ioDispatcher) {
            reviewApi.addMovieReview(reviewModify.toReviewModifyDto(), movieId)
        }
    }

    override suspend fun editMovieReview(reviewModify: ReviewModify, movieId: String, id: String) {
        withContext(ioDispatcher) {
            reviewApi.editMovieReview(reviewModify.toReviewModifyDto(), movieId, id)
        }
    }

    override suspend fun deleteMovieReview(movieId: String, id: String) {
        withContext(ioDispatcher) { reviewApi.deleteMovieReview(movieId, id) }
    }
}