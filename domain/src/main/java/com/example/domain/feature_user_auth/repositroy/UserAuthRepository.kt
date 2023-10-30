package com.example.domain.feature_user_auth.repositroy

import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.TokenResponse
import com.example.domain.feature_user_auth.model.UserRegistration

interface UserAuthRepository {
    suspend fun register(userRegistration: UserRegistration)

    suspend fun login(loginRequest: LoginRequest)

    fun getTokenResponse(): TokenResponse?
}