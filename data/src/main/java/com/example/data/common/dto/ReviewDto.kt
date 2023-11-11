package com.example.data.common.dto

data class ReviewDto(
    val author: UserShortDto?,
    val createDateTime: String,
    val id: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)