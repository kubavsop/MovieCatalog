package com.example.data.feature_film_screen.repository

import com.example.data.feature_film_screen.remote.ReviewApi
import com.example.data.mapper.toReviewModifyDto
import com.example.domain.feature_film_screen.repository.FilmRepository
import com.example.domain.model.ReviewModify

class FilmRepositoryImpl(
    private val reviewApi: ReviewApi
) : FilmRepository {
    override suspend fun addMovieReview(reviewModify: ReviewModify, movieId: String) {
        reviewApi.addMovieReview(reviewModify.toReviewModifyDto(), movieId)
    }

    override suspend fun editMovieReview(reviewModify: ReviewModify, movieId: String, id: String) {
        reviewApi.editMovieReview(reviewModify.toReviewModifyDto(), movieId, id)
    }

    override suspend fun deleteMovieReview(movieId: String, id: String) {
        reviewApi.deleteMovieReview(movieId, id)
    }
}