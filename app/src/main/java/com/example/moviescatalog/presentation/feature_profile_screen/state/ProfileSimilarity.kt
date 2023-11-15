package com.example.moviescatalog.presentation.feature_profile_screen.state

data class ProfileSimilarity(
    val avatarLink: Boolean = true,
    val birthDate: Boolean = true,
    val email: Boolean = true,
    val gender: Boolean = true,
    val name: Boolean = true,
    val nickName: Boolean = true,
)
