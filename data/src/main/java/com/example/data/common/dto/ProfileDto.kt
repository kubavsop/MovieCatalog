package com.example.data.common.dto

import java.util.UUID

data class ProfileDto(
    val avatarLink: String?,
    val birthDate: String,
    val email: String,
    val gender: Int,
    val id: UUID,
    val name: String,
    val nickName: String
)