package com.example.data.dto

data class ReviewModifyDto(
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String
)