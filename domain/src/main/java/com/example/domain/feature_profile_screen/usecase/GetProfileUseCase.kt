package com.example.domain.feature_profile_screen.usecase

import com.example.domain.feature_profile_screen.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Profile {
        return repository.getProfile()
    }
}