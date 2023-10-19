package com.example.moviescatalog.presentation.password_registration

import com.example.moviescatalog.presentation.UiText

data class PasswordRegistrationState(
    val repeatedPasswordError: UiText? = null,
    val passwordError: UiText? = null,
    val registrationError: String? = null,
    val isLoading: Boolean = false,
    val isRegistered: Boolean = false,
    val isValid: Boolean = false,
    val test: String = ""
)
