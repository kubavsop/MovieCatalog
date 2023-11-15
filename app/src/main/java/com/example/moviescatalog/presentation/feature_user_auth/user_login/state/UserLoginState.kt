package com.example.moviescatalog.presentation.feature_user_auth.user_login.state

import com.example.moviescatalog.presentation.util.UiText

sealed interface UserLoginState {
    data object Initial : UserLoginState
    data object Loading : UserLoginState
    data object Success : UserLoginState

    data class UserLogin(
        val loginError: UiText? = null,
        val passwordError: UiText? = null,
        val isValid: Boolean = false
    ): UserLoginState

    data class LoginError(val msg: UiText) : UserLoginState
}