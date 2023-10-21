package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_user_auth.validator.EmailPatternValidator

class ValidateEmailUseCase(
    private val validator: EmailPatternValidator
) {
    operator fun invoke(email: String): Boolean {
        return validator.isValidEmail(email = email)
    }
}