package com.example.domain.feature_user_auth.validator

interface EmailPatternValidator {
    fun isValidEmail(email: String): Boolean
}