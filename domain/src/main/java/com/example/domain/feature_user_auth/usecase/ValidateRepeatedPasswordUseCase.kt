package com.example.domain.feature_user_auth.usecase

class ValidateRepeatedPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): Boolean {
        return password == repeatedPassword
    }
}