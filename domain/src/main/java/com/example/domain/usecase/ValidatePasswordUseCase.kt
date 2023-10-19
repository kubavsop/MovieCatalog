package com.example.domain.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        if (password.length < 6) {
            return false
        }
        return true
    }
}