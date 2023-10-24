package com.example.domain.feature_main_screen.model

data class MovieElement(
    val country: String,
    val genres: List<Genre>,
    val id: String,
    val name: String,
    val poster: String,
    val reviews: List<ReviewShort>,
    val year: Int
)
