package com.example.data.dto

data class MovieDetailsDto(
    val ageLimit: Int,
    val budget: Int,
    val country: String,
    val description: String,
    val director: String,
    val fees: Int,
    val genres: List<GenreDto>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewDto>,
    val tagline: String,
    val time: Int,
    val year: Int
)