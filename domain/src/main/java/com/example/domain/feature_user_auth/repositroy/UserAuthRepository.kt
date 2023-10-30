package com.example.domain.feature_user_auth.repositroy

import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.Profile
import com.example.domain.feature_user_auth.model.TokenResponse

interface UserAuthRepository {
    suspend fun register(profile: Profile)

    suspend fun login(loginRequest: LoginRequest)

    fun getTokenResponse(): TokenResponse?
}