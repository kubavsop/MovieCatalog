package com.example.domain.feature_user_auth.usecase

import com.example.domain.model.TokenResponse
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class GetTokenUseCase(private val repository: UserAuthRepository) {
    operator fun invoke(): TokenResponse? {
        return repository.getTokenResponse()
    }
}