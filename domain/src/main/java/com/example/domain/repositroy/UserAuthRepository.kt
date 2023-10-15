package com.example.domain.repositroy

import com.example.domain.model.LoginRequest
import com.example.domain.model.Profile
import com.example.domain.model.TokenResponse

interface UserAuthRepository {
    suspend fun register(profile: Profile): TokenResponse

    suspend fun login(loginRequest: LoginRequest): TokenResponse
}