package com.example.moviescatalog.presentation.user_login

sealed interface UserLoginState {
    data object Initial : UserLoginState
    data object Loading : UserLoginState
    data object Success : UserLoginState
    data class Error(val msg: String) : UserLoginState
    data class Test(val msg: String): UserLoginState
}