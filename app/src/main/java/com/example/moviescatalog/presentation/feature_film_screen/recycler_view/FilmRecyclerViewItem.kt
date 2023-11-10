package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import com.example.domain.model.Review

sealed interface FilmRecyclerViewItem {
    data class HeaderItem(
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
        val tagline: String,
        val time: Int,
        val year: Int
    ) : FilmRecyclerViewItem

    data class ReviewItem(
        val review: Review
    ) : FilmRecyclerViewItem
}