package com.example.data.validator

import android.util.Patterns
import com.example.domain.validator.EmailPatternValidator

class EmailPatternValidatorImpl: EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}