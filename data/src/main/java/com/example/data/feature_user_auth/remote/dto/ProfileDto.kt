package com.example.data.feature_user_auth.remote.dto

data class ProfileDto(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
