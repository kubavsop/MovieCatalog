package com.example.data.feature_user_auth.local

interface UserStorage {
    fun saveToken(tokenResponseEntity: TokenResponseEntity)
    fun getToken(): TokenResponseEntity?
}