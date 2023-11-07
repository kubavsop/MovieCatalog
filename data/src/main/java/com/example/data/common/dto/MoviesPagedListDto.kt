package com.example.data.common.dto

data class MoviesPagedListDto(
    val movies: List<MovieElementDto>,
    val pageInfo: PageInfoDto
)