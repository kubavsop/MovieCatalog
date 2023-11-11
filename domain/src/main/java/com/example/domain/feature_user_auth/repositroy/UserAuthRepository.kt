package com.example.domain.feature_user_auth.repositroy

import com.example.domain.model.LoginRequest
import com.example.domain.model.TokenResponse
import com.example.domain.model.User
import com.example.domain.model.UserRegistration

interface UserAuthRepository {
    suspend fun register(userRegistration: UserRegistration): TokenResponse

    suspend fun login(loginRequest: LoginRequest): TokenResponse

    fun getTokenResponse(): TokenResponse?

    fun saveTokenResponse(tokenResponse: TokenResponse)

    fun saveUser(id: User)
    fun getUser(): User?
}