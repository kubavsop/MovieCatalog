package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.TokenResponse
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class LoginUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): TokenResponse {
        return repository.login(loginRequest)
    }
}