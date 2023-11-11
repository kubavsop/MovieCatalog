package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.model.User

class GetUserIdUseCase(
    private val repository: UserAuthRepository
) {
    operator fun invoke(): User? {
        return repository.getUser()
    }
}