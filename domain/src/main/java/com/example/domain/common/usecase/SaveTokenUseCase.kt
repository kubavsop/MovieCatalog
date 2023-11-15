package com.example.domain.common.usecase

import com.example.domain.common.model.TokenResponse
import com.example.domain.common.repository.UserRepository

class SaveTokenUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(tokenResponse: TokenResponse) {
        repository.saveTokenResponse(tokenResponse)
    }
}