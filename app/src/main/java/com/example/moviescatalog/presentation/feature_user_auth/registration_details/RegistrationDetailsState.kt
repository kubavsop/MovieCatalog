package com.example.moviescatalog.presentation.feature_user_auth.registration_details

import com.example.moviescatalog.presentation.UiText

private const val EMPTY_STRING = ""
private const val MALE = "Мужчина"

data class RegistrationDetailsState(
    val birthday: String = EMPTY_STRING,
    val gender: String = MALE,
    val loginError: UiText? = null,
    val emailError: UiText? = null,
    val firstNameError: UiText? = null,
    val isValid: Boolean = false
)
