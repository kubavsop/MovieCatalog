package com.example.data.common.dto

data class ReviewModifyDto(
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)