package com.example.data.remote.dto

data class ProfileDto(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
