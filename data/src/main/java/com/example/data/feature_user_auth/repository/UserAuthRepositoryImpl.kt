package com.example.data.feature_user_auth.repository

import com.example.data.feature_user_auth.mapper.toLoginRequestDto
import com.example.data.feature_user_auth.mapper.toProfileDto
import com.example.data.feature_user_auth.mapper.toTokenResponse
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.UserRegistration
import com.example.domain.feature_user_auth.model.TokenResponse
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi
) : UserAuthRepository {
    override suspend fun register(profile: UserRegistration): TokenResponse {
        return userAuthApi.register(profile.toProfileDto()).toTokenResponse()
    }

    override suspend fun login(loginRequest: LoginRequest): TokenResponse {
        return userAuthApi.login(loginRequest.toLoginRequestDto()).toTokenResponse()
    }
}