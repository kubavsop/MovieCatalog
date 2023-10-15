package com.example.domain.usecase

import com.example.domain.model.LoginRequest
import com.example.domain.model.TokenResponse
import com.example.domain.repositroy.UserAuthRepository

class LoginUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend operator fun invoke(loginRequest: LoginRequest): TokenResponse {
        return repository.login(loginRequest)
    }
}