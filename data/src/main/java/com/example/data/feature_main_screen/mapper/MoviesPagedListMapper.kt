package com.example.data.feature_main_screen.mapper

import com.example.data.feature_main_screen.remote.dto.GenreDto
import com.example.data.feature_main_screen.remote.dto.MovieElementDto
import com.example.data.feature_main_screen.remote.dto.MoviesPagedListDto
import com.example.data.feature_main_screen.remote.dto.PageInfoDto
import com.example.data.feature_main_screen.remote.dto.ReviewShortDto
import com.example.domain.feature_main_screen.model.Genre
import com.example.domain.feature_main_screen.model.MovieElement
import com.example.domain.feature_main_screen.model.MoviesPagedList
import com.example.domain.feature_main_screen.model.PageInfo
import com.example.domain.feature_main_screen.model.ReviewShort

fun MoviesPagedListDto.toMoviesPagedList() = MoviesPagedList(
    movies = movies.map { it.toMovieElement() },
    pageInfo = pageInfo.toPageInfo()
)

fun MoviesPagedList.toMoviesPagedListDto() = MoviesPagedListDto(
    movies = movies.map { it.toMovieElementDto() },
    pageInfo = pageInfo.toPageInfoDto()
)

fun MovieElementDto.toMovieElement() = MovieElement(
    country = country,
    genres = genres.map { it.toGenre() },
    id = id,
    name = name,
    poster = poster,
    reviews = reviews.map { it.toReviewShort() },
    year = year
)

fun MovieElement.toMovieElementDto() = MovieElementDto(
    country = country,
    genres = genres.map { it.toGenreDto() },
    id = id,
    name = name,
    poster = poster,
    reviews = reviews.map { it.toReviewShortDto() },
    year = year
)

fun PageInfoDto.toPageInfo() = PageInfo(
    currentPage = currentPage,
    pageCount = pageCount,
    pageSize = pageSize
)

fun PageInfo.toPageInfoDto() = PageInfoDto(
    currentPage = currentPage,
    pageCount = pageCount,
    pageSize = pageSize
)

fun GenreDto.toGenre() = Genre(
    id = id,
    name = name
)

fun Genre.toGenreDto() = GenreDto(
    id = id,
    name = name
)

fun ReviewShortDto.toReviewShort() = ReviewShort(
    id = id,
    rating = rating
)

fun ReviewShort.toReviewShortDto() = ReviewShortDto(
    id = id,
    rating = rating
)