package com.example.domain.model

data class MoviesPagedList(
    val movies: List<MovieElement>,
    val pageInfo: PageInfo
)
