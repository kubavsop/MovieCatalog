package com.example.moviescatalog.presentation.feature_user_auth.registration_details.state

data class DetailsIsNotEmptyState (
    val birthday: Boolean = false,
    val login: Boolean = false,
    val email: Boolean = false,
    val firstName: Boolean = false,
)