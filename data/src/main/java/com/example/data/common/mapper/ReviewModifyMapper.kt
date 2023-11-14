package com.example.data.common.mapper

import com.example.data.common.dto.ReviewModifyDto
import com.example.domain.common.model.ReviewModify

fun ReviewModify.toReviewModifyDto() = ReviewModifyDto(
    isAnonymous = isAnonymous,
    rating = rating,
    reviewText = reviewText
)