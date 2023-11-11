package com.example.domain.model

data class MovieElement(
    val country: String,
    val genres: List<String>,
    val id: String,
    val name: String,
    val poster: String,
    val averageRating: Double,
    val userRating: Int? = null,
    val year: Int
)
