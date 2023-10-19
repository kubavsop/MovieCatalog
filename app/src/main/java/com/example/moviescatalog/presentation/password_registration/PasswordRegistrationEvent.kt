package com.example.moviescatalog.presentation.password_registration


sealed interface PasswordRegistrationEvent {
    data class PasswordChanged(val password: String, val repeatedPassword: String) :
        PasswordRegistrationEvent
    data object Register : PasswordRegistrationEvent
}