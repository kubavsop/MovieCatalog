package com.example.data.feature_user_auth.validator

import android.util.Patterns
import com.example.domain.feature_user_auth.validator.EmailPatternValidator

class EmailPatternValidatorImpl: EmailPatternValidator {
    override fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}