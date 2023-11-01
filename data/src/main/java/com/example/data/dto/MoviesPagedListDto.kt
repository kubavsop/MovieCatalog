package com.example.data.dto

data class MoviesPagedListDto(
    val movies: List<MovieElementDto>,
    val pageInfo: PageInfoDto
)