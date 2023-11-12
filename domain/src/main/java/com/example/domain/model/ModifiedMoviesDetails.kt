package com.example.domain.model

data class ModifiedMoviesDetails (
    val ageLimit: Int,
    val budget: Int,
    val haveReview: Boolean,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<String>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ModifiedReview>,
    val tagline: String,
    val time: Int,
    val year: Int,
    val inFavorite: Boolean,
    val averageRating: Double,
)