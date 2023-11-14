package com.example.moviescatalog.presentation.feature_user_auth.user_login

sealed interface UserLoginEvent {
    data class Login(val username: String,val password: String): UserLoginEvent

    data class LoginChanged(val login: String): UserLoginEvent

    data class PasswordChanged(val password: String): UserLoginEvent
    data object UserLogin: UserLoginEvent
}