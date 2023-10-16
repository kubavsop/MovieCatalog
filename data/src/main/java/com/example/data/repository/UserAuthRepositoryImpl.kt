package com.example.data.repository

import com.example.data.mapper.toLoginRequestDto
import com.example.data.mapper.toProfileDto
import com.example.data.mapper.toTokenResponse
import com.example.data.remote.UserAuthApi
import com.example.domain.model.LoginRequest
import com.example.domain.model.Profile
import com.example.domain.model.TokenResponse
import com.example.domain.repositroy.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi
) : UserAuthRepository {
    override suspend fun register(profile: Profile): TokenResponse {
        return userAuthApi.register(profile.toProfileDto()).toTokenResponse()
    }

    override suspend fun login(loginRequest: LoginRequest): TokenResponse {
        return userAuthApi.login(loginRequest.toLoginRequestDto()).toTokenResponse()
    }
}