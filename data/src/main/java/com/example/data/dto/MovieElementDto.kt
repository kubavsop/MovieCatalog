package com.example.data.dto

data class MovieElementDto(
    val country: String,
    val genres: List<GenreDto>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewShortDto>,
    val year: Int
)