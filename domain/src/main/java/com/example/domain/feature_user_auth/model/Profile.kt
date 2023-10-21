package com.example.domain.feature_user_auth.model

data class Profile(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
