package com.example.domain.usecase

class ValidateLoginUseCase {
    operator fun invoke(firstName: String): Boolean {
        if (firstName.length < 2) {
            return false
        }
        return true
    }
}