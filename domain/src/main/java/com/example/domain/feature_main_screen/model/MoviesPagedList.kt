package com.example.domain.feature_main_screen.model

data class MoviesPagedList(
    val movies: List<MovieElement>,
    val pageInfo: PageInfo
)
