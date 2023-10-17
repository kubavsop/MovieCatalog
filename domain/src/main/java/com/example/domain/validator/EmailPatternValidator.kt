package com.example.domain.validator

interface EmailPatternValidator {
    fun isValidEmail(email: String): Boolean
}