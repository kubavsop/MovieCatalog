package com.example.domain.feature_main_screen.model

data class MovieDetails(
    val ageLimit: Int,
    val budget: Int,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<String>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<Review>,
    val tagline: String,
    val time: Int,
    val year: Int
)
