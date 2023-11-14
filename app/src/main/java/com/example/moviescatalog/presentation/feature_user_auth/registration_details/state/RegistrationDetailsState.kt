package com.example.moviescatalog.presentation.feature_user_auth.registration_details.state

import com.example.moviescatalog.presentation.UiText

private const val EMPTY_STRING = ""
data class RegistrationDetailsState(
    val birthday: String = EMPTY_STRING,
    val loginError: UiText? = null,
    val emailError: UiText? = null,
    val firstNameError: UiText? = null,
    val isValid: Boolean = false
)
