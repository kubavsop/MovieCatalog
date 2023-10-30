package com.example.domain.feature_profile_screen.usecase

import com.example.domain.feature_profile_screen.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke() {
        return repository.logout()
    }
}