package com.example.moviescatalog.presentation.feature_profile_screen

import com.example.moviescatalog.presentation.feature_profile_screen.state.Gender


sealed interface ProfileEvent {
    data object Cancel: ProfileEvent
    data object ShowProfile: ProfileEvent

    data class EmailChanged(val email: String): ProfileEvent
    data class FirstNameChanged(val firstName: String): ProfileEvent
    data class AvatarChanged(val avatarLink: String): ProfileEvent
    data class GenderChanged(val gender: Gender): ProfileEvent
    data class BirthdayChanged(val year: Int, val monthOfYear: Int,val dayOfMonth: Int):
        ProfileEvent

    data class ChangeProfile(
        val avatarLink: String?,
        val email: String,
        val name: String,
    ): ProfileEvent

    data object Exit: ProfileEvent
}