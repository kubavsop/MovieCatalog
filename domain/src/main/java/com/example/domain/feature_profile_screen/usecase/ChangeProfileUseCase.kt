package com.example.domain.feature_profile_screen.usecase

import com.example.domain.feature_profile_screen.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository

class ChangeProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        repository.changeProfile(profile)
    }
}