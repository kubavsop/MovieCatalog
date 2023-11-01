package com.example.domain.feature_film_screen.repository

import com.example.domain.model.ReviewModify

interface FilmRepository {

    suspend fun addMovieReview(reviewModify: ReviewModify, movieId: String)

    suspend fun editMovieReview(
        reviewModify: ReviewModify,
        movieId: String,
        id: String
    )

    suspend fun deleteMovieReview(
        movieId: String,
        id: String
    )
}