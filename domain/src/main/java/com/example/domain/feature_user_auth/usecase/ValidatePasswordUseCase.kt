package com.example.domain.feature_user_auth.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        return password.length >= 6
    }
}