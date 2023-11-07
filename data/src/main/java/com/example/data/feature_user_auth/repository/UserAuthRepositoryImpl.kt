package com.example.data.feature_user_auth.repository

import com.example.data.feature_user_auth.local.UserStorage
import com.example.data.common.mapper.toLoginRequestDto
import com.example.data.common.mapper.toTokenResponse
import com.example.data.common.mapper.toTokenResponseEntity
import com.example.data.common.mapper.toUserRegistrationDto
import com.example.data.feature_user_auth.remote.UserAuthApi
import com.example.domain.model.LoginRequest
import com.example.domain.model.TokenResponse
import com.example.domain.model.UserRegistration
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserAuthRepositoryImpl(
    private val userAuthApi: UserAuthApi,
    private val userStorage: UserStorage,
    private val ioDispatcher: CoroutineDispatcher
) : UserAuthRepository {
    override suspend fun register(userRegistration: UserRegistration) {
        withContext(ioDispatcher) {
            val tokenResponse = userAuthApi.register(userRegistration.toUserRegistrationDto())
            userStorage.saveToken(tokenResponseEntity = tokenResponse.toTokenResponseEntity())
        }
    }

    override suspend fun login(loginRequest: LoginRequest) {
        withContext(ioDispatcher) {
            val tokenResponse = userAuthApi.login(loginRequest.toLoginRequestDto())
            userStorage.saveToken(tokenResponseEntity = tokenResponse.toTokenResponseEntity())
        }
    }

    override fun getTokenResponse(): TokenResponse? {
        return userStorage.getToken()?.toTokenResponse()
    }
}