package com.example.moviescatalog.presentation.registration_details

sealed class RegistrationDetailsEvent {
    data class EmailChanged(val email: String): RegistrationDetailsEvent()
    data class GenderChanged(val gender: String): RegistrationDetailsEvent()
    data class FirstNameChanged(val firstName: String): RegistrationDetailsEvent()
    data class LoginChanged(val login: String): RegistrationDetailsEvent()
    data class BirthdayChanged(val birthday: String): RegistrationDetailsEvent()
}