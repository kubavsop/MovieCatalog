package com.example.domain.model

data class Profile(
    val avatarLink: String?,
    val birthDate: String,
    val email: String,
    val gender: Int,
    val id: String,
    val name: String,
    val nickName: String
)