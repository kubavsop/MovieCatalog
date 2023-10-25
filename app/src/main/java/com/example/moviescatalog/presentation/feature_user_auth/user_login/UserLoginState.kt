package com.example.moviescatalog.presentation.feature_user_auth.user_login

import com.example.moviescatalog.presentation.UiText

sealed interface UserLoginState {
    data object Initial : UserLoginState
    data object Loading : UserLoginState
    data object Success : UserLoginState
    data class Error(val msg: UiText) : UserLoginState
}