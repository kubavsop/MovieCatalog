package com.example.moviescatalog.presentation.feature_film_screen.recycler_view

import com.example.domain.common.model.UserShort
import com.example.moviescatalog.presentation.util.UiText

sealed interface FilmRecyclerViewItem {
    data class HeaderItem(
        val ageLimit: UiText,
        val id: String,
        val inFavorite: Boolean,
        val budget: UiText,
        val haveReview: Boolean,
        val country: UiText,
        val description: String,
        val director: UiText,
        val fees: UiText,
        val genres: List<String>,
        val name: String,
        val poster: String,
        val tagline: UiText,
        val time: UiText,
        val year: UiText,
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