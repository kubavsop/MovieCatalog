package com.example.data.mapper

import com.example.data.dto.MovieDetailsDto
import com.example.data.dto.ReviewDto
import com.example.data.dto.UserShortDto
import com.example.domain.model.MovieDetails
import com.example.domain.model.Review
import com.example.domain.model.UserShort

fun MovieDetailsDto.toMovieDetails() = MovieDetails(
    ageLimit = ageLimit,
    budget = budget,
    country = country,
    description = description,
    director = director,
    fees = fees,
    genres = genres.map { it.name},
    id = id,
    name = name,
    poster = poster,
    reviews = reviews.map { it.toReview() },
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