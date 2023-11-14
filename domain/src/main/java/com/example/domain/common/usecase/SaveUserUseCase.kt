package com.example.domain.common.usecase

import com.example.domain.common.model.User
import com.example.domain.common.repository.UserRepository

class SaveUserUseCase(
    private val repository: UserRepository
) {
    operator fun invoke(user: User) {
        repository.saveUser(user)
    }
}