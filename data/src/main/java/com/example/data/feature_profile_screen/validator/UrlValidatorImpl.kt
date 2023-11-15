package com.example.data.feature_profile_screen.validator

import android.util.Patterns
import com.example.domain.feature_profile_screen.validator.UrlValidator

class UrlValidatorImpl: UrlValidator {
    override fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
}