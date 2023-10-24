package com.example.data.feature_main_screen.mapper

import com.example.data.feature_main_screen.remote.dto.MovieDetailsDto
import com.example.data.feature_main_screen.remote.dto.ReviewDto
import com.example.data.feature_main_screen.remote.dto.UserShortDto
import com.example.domain.feature_main_screen.model.MovieDetails
import com.example.domain.feature_main_screen.model.Review
import com.example.domain.feature_main_screen.model.UserShort

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    ageLimit = ageLimit,
    budget = budget,
    country = country,
    description = description,
    director = director,
    fees = fees,
    genres = genres.map { it.toGenre() },
    id = id,
    name = name,
    poster = poster,
    reviews = reviews.map { it.toReview() },
    tagline = tagline,
    time = time,
    year = year
)

fun MovieDetails.toMovieDetailsDto() = MovieDetailsDto(
    ageLimit = ageLimit,
    budget = budget,
    country = country,
    description = description,
    director = director,
    fees = fees,
    genres = genres.map { it.toGenreDto() },
    id = id,
    name = name,
    poster = poster,
    reviews = reviews.map { it.toReviewDto() },
    tagline = tagline,
    time = time,
    year = year
)

fun UserShortDto.toUserShort() = UserShort(
    avatar = avatar,
    nickName = nickName,
    userId = userId
)

fun UserShort.toUserShortDto() = UserShortDto(
    avatar = avatar,
    nickName = nickName,
    userId = userId
)

fun ReviewDto.toReview() = Review(
    author = author.toUserShort(),
    createDateTime = createDateTime,
    id = id,
    isAnonymous = isAnonymous,
    rating = rating,
    reviewText = reviewText
)

fun Review.toReviewDto() = ReviewDto(
    author = author.toUserShortDto(),
    createDateTime = createDateTime,
    id = id,
    isAnonymous = isAnonymous,
    rating = rating,
    reviewText = reviewText
)