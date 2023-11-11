package com.example.domain.model

import java.util.UUID

data class Profile(
    val avatarLink: String?,
    val birthDate: String,
    val email: String,
    val gender: Int,
    val id: UUID,
    val name: String,
    val nickName: String
)
