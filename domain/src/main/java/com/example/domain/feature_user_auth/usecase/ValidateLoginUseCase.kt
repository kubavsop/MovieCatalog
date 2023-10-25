package com.example.domain.feature_user_auth.usecase

class ValidateLoginUseCase {
    operator fun invoke(login: String): Boolean {
        return login.length >= 2
    }
}