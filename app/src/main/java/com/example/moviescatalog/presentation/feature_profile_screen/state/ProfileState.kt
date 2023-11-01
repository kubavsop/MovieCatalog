package com.example.moviescatalog.presentation.feature_profile_screen.state

import com.example.moviescatalog.presentation.UiText


sealed interface ProfileState {

    data class Profile(
        val avatarLink: String?,
        val birthDate: String,
        val email: String,
        val gender: Int,
        val id: String,
        val name: String,
        val nickName: String,
    ): ProfileState

    data class ProfileChanged(
        val birthDate: String = "",
        val gender: Gender,
        val emailError: UiText? = null,
        val firstNameError: UiText? = null,
        val isValid: Boolean = false
    ) : ProfileState

    data object Loading: ProfileState
    data object Initial: ProfileState
}