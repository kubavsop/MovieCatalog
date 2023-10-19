package com.example.domain.usecase

class ValidatePasswordUseCase {
    operator fun invoke(password: String): Boolean {
        if (password.length < 5) {
            return false
        }
        if (password.any { it.isDigit() } && password.any { it.isLetter() }) {
            return true
        }
        return false
    }
}