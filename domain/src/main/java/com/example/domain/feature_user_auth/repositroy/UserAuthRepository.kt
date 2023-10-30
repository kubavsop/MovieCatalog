package com.example.domain.feature_user_auth.repositroy

import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.UserRegistration
import com.example.domain.feature_user_auth.model.TokenResponse

interface UserAuthRepository {
    suspend fun register(profile: UserRegistration): TokenResponse

    suspend fun login(loginRequest: LoginRequest): TokenResponse
}