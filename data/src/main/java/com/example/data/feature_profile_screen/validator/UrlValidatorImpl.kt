package com.example.data.feature_profile_screen.validator

import android.util.Patterns

class UrlValidatorImpl {
    fun isUrlValid(url: String): Boolean {
        return Patterns.WEB_URL.matcher(url).matches()
    }
}