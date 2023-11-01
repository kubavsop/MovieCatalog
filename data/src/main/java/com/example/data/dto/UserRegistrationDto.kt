package com.example.data.dto

data class UserRegistrationDto(
    val userName: String,
    val name: String,
    val password: String,
    val email: String,
    val birthDate: String,
    val gender: Int
)
