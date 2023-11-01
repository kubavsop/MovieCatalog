package com.example.data.mapper

import com.example.data.dto.ReviewModifyDto
import com.example.domain.model.ReviewModify

fun ReviewModify.toReviewModifyDto() = ReviewModifyDto(
    isAnonymous = isAnonymous,
    rating = rating,
    reviewText = reviewText
)