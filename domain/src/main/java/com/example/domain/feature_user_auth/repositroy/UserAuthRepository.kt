package com.example.domain.feature_user_auth.repositroy

import com.example.domain.common.model.LoginRequest
import com.example.domain.common.model.TokenResponse
import com.example.domain.common.model.User
import com.example.domain.common.model.UserRegistration

interface UserAuthRepository {
    suspend fun register(userRegistration: UserRegistration): TokenResponse

    suspend fun login(loginRequest: LoginRequest): TokenResponse
}