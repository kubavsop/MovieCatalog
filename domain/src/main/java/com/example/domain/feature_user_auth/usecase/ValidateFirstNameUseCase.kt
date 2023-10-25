package com.example.domain.feature_user_auth.usecase

class ValidateFirstNameUseCase {
    operator fun invoke(firstName: String): Boolean {
        return firstName.length >= 2
    }
}