package com.example.moviescatalog.presentation.feature_user_auth.user_login

sealed interface UserLoginEvent {
    data class Login(val username: String,val password: String): UserLoginEvent
    data object Initial: UserLoginEvent
}