package com.example.domain.common.usecase

import com.example.domain.common.model.User
import com.example.domain.common.repository.UserRepository

class GetUserIdUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(): User? {
        return repository.getUser()
    }
}