package com.example.moviescatalog.presentation.feature_profile_screen.state

data class ProfileSimilarity(
    var avatarLink: Boolean = true,
    var birthDate: Boolean = true,
    var email: Boolean = true,
    var gender: Boolean = true,
    var id: Boolean = true,
    var name: Boolean = true,
    var nickName: Boolean = true,
)
