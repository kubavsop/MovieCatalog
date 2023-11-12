package com.example.domain.model

data class ModifiedReview (
    val author: UserShort?,
    val createDateTime: String,
    val id: String,
    val isAnonymous: Boolean,
    val rating: Int,
    val reviewText: String,
    val isMine: Boolean
)