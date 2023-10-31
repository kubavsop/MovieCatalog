package com.example.data.feature_user_auth.repository

import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.mapper.toLoginRequestDto
import com.example.data.mapper.toTokenResponse
import com.example.data.mapper.toTokenResponseEntity
import com.example.data.mapper.toUserRegistrationDto
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.domain.model.LoginRequest
import com.example.domain.model.TokenResponse
import com.example.domain.model.UserRegistration
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi,
    private val userStorage: UserStorage
) : UserAuthRepository {
    override suspend fun register(userRegistration: UserRegistration) {
        val tokenResponse = userAuthApi.register(userRegistration.toUserRegistrationDto())
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