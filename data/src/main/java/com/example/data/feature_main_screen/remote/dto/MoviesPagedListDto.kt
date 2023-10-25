package com.example.data.feature_main_screen.remote.dto

data class MoviesPagedListDto(
    val movies: List<MovieElementDto>,
    val pageInfo: PageInfoDto
)