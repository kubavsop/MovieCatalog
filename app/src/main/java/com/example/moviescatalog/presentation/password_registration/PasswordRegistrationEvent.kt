package com.example.moviescatalog.presentation.password_registration


sealed interface PasswordRegistrationEvent {
    data class PasswordChanged(val password: String, val repeatedPassword: String) :
        PasswordRegistrationEvent
    data class Register(
        val userName: String,
        val name: String,
        val email: String,
        val password: String,
        val birthDate: String,
        val gender: String
    ): PasswordRegistrationEvent
}