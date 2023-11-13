package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import com.example.domain.model.UserShort

sealed interface FilmRecyclerViewItem {
    data class HeaderItem(
        val ageLimit: Int,
        val id: String,
        val inFavorite: Boolean,
        val budget: Int,
        val haveReview: Boolean,
        val country: String,
        val description: String,
        val director: String,
        val fees: Int,
        val genres: List<String>,
        val name: String,
        val poster: String,
        val tagline: String,
        val time: Int,
        val year: Int,
        val averageRating: Double
    ) : FilmRecyclerViewItem

    data class ReviewItem(
        val author: UserShort?,
        val createDateTime: String,
        val id: String,
        val isAnonymous: Boolean,
        val rating: Int,
        val reviewText: String,
        val isMine: Boolean
    ) : FilmRecyclerViewItem
}