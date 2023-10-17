package com.example.domain.usecase

import com.example.domain.validator.EmailPatternValidator

class ValidateEmailUseCase(
    private val validator: EmailPatternValidator
) {
    operator fun invoke(email: String): Boolean {
        return validator.isValidEmail(email = email)
    }
}