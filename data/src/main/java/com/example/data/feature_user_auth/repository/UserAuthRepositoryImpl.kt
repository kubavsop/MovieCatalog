package com.example.data.feature_user_auth.repository

import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.feature_user_auth.mapper.toLoginRequestDto
import com.example.data.feature_user_auth.mapper.toProfileDto
import com.example.data.feature_user_auth.mapper.toTokenResponse
import com.example.data.feature_user_auth.mapper.toTokenResponseEntity
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.Profile
import com.example.domain.feature_user_auth.model.TokenResponse
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi,
    private val userStorage: UserStorage
) : UserAuthRepository {
    override suspend fun register(profile: Profile) {
        val tokenResponse = userAuthApi.register(profile.toProfileDto())
        userStorage.saveToken(tokenResponseEntity = tokenResponse.toTokenResponseEntity())
    }

    override suspend fun login(loginRequest: LoginRequest) {
        val tokenResponse = userAuthApi.login(loginRequest.toLoginRequestDto())
        userStorage.saveToken(tokenResponseEntity = tokenResponse.toTokenResponseEntity())
    }

    override fun getTokenResponse(): TokenResponse? {
        return userStorage.getToken()?.toTokenResponse()
    }
}