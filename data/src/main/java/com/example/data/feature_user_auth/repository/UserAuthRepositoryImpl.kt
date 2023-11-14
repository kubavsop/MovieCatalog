package com.example.data.feature_user_auth.repository

import com.example.data.common.mapper.toLoginRequestDto
import com.example.data.common.mapper.toTokenResponse
import com.example.data.common.mapper.toTokenResponseEntity
import com.example.data.common.mapper.toUser
import com.example.data.common.mapper.toUserEntity
import com.example.data.common.mapper.toUserRegistrationDto
import com.example.data.common.remote.MovieCatalogApi
import com.example.data.common.local.UserStorage
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.common.model.LoginRequest
import com.example.domain.common.model.TokenResponse
import com.example.domain.common.model.User
import com.example.domain.common.model.UserRegistration
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class UserAuthRepositoryImpl(
    private val userAuthApi: MovieCatalogApi,
    private val ioDispatcher: CoroutineDispatcher
) : UserAuthRepository {
    override suspend fun register(userRegistration: UserRegistration): TokenResponse {
        return withContext(ioDispatcher) {
            userAuthApi.register(userRegistration.toUserRegistrationDto())
        }.toTokenResponse()
    }

    override suspend fun login(loginRequest: LoginRequest): TokenResponse {
        return withContext(ioDispatcher) {
            userAuthApi.login(loginRequest.toLoginRequestDto())
        }.toTokenResponse()
    }
}