package com.example.domain.feature_profile_screen.usecase

import com.example.domain.feature_profile_screen.validator.UrlValidator

class ValidateUrlUseCase(
    private val validator: UrlValidator
) {
    operator fun invoke(url: String): Boolean {
        return validator.isUrlValid(url)
    }
}