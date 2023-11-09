package com.example.moviescatalog.presentation.feature_main_screen.recycler_view

import com.example.domain.model.MovieElement

sealed interface MainRecyclerViewItem {
    data class HeaderItem(
        val movies: List<MovieElement>
    ): MainRecyclerViewItem

    data class MovieItem(
        val movieElement: MovieElement
    ): MainRecyclerViewItem
}