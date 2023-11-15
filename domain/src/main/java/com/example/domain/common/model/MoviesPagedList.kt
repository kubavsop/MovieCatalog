package com.example.domain.common.model

data class MoviesPagedList(
    val movies: List<MovieElement>,
    val pageInfo: PageInfo
)
