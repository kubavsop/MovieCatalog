package com.example.moviescatalog.presentation.registration_details

import com.example.moviescatalog.presentation.UiText

private const val EMPTY_STRING = ""
private const val MALE = "Мужской"

data class RegistrationDetailsFormState(
    val birthday: String = EMPTY_STRING,
    val gender: String = MALE,
    val loginError: UiText? = null,
    val emailError: UiText? = null,
    val firstNameError: UiText? = null,
    val success: Boolean = false,
)
