package com.example.moviescatalog.presentation.feature_user_auth.password_registration

import com.example.moviescatalog.presentation.UiText

sealed interface PasswordRegistrationState {
    data class PasswordRegistration(
        val repeatedPasswordError: UiText? = null,
        val passwordError: UiText? = null,
        val isValid: Boolean = false,
    ): PasswordRegistrationState

    data class RegistrationError(val msg: UiText): PasswordRegistrationState
    data object Initial: PasswordRegistrationState
    data object Registered: PasswordRegistrationState
    data object Loading: PasswordRegistrationState
}