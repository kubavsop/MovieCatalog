package com.example.domain.usecase

import com.example.domain.model.Profile
import com.example.domain.model.TokenResponse
import com.example.domain.repositroy.UserAuthRepository

class RegisterUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend operator fun invoke(profile: Profile): TokenResponse {
        return repository.register(profile)
    }
}