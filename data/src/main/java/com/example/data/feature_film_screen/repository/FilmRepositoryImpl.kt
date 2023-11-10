package com.example.data.feature_film_screen.repository

import com.example.data.common.mapper.toMovieDetails
import com.example.data.feature_film_screen.remote.FilmApi
import com.example.data.common.mapper.toReviewModifyDto
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.model.MovieDetails
import com.example.domain.model.ReviewModify
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FilmRepositoryImpl(
    private val filmApi: FilmApi,
    private val ioDispatcher: CoroutineDispatcher
) : FilmRepository {
    override suspend fun addMovieReview(reviewModify: ReviewModify, movieId: String) {
        withContext(ioDispatcher) {
            filmApi.addMovieReview(reviewModify.toReviewModifyDto(), movieId)
        }
    }

    override suspend fun editMovieReview(reviewModify: ReviewModify, movieId: String, id: String) {
        withContext(ioDispatcher) {
            filmApi.editMovieReview(reviewModify.toReviewModifyDto(), movieId, id)
        }
    }

    override suspend fun deleteMovieReview(movieId: String, id: String) {
        withContext(ioDispatcher) { filmApi.deleteMovieReview(movieId, id) }
    }

    override suspend fun getMovieDetailsById(id: String): MovieDetails {
        return withContext(ioDispatcher) {
            filmApi.getMovieDetailsById(id = id).toMovieDetails()
        }
    }
}