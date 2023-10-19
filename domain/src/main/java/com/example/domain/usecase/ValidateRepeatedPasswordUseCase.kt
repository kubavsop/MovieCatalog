package com.example.domain.usecase

class ValidateRepeatedPasswordUseCase {
    operator fun invoke(password: String, repeatedPassword: String): Boolean {
        return password == repeatedPassword
    }
}