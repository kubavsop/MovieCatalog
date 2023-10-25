package com.example.moviescatalog.presentation.feature_user_auth.registration_details

sealed interface RegistrationDetailsEvent {
    data class EmailChanged(val email: String): RegistrationDetailsEvent
    data class FirstNameChanged(val firstName: String): RegistrationDetailsEvent
    data class LoginChanged(val login: String): RegistrationDetailsEvent
    data class BirthdayChanged(val year: Int, val monthOfYear: Int,val dayOfMonth: Int):
        RegistrationDetailsEvent
}