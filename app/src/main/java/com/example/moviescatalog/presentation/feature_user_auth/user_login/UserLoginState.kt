package com.example.moviescatalog.presentation.feature_user_auth.user_login

sealed interface UserLoginState {
    data object Initial : UserLoginState
    data object Loading : UserLoginState
    data object Success : UserLoginState
    data class Error(val msg: String) : UserLoginState
}