package com.example.domain.common.usecase

import com.example.domain.common.repository.UserRepository

class RemoveTokenUseCase(
    private val repository: UserRepository
) {
    operator fun invoke() {
        repository.removeToken()
    }
}