package com.example.moviescatalog.presentation.feature_user_auth.user_login.state

data class LoginIsNotEmptyState (
    val password: Boolean = false,
    val login: Boolean = false
)