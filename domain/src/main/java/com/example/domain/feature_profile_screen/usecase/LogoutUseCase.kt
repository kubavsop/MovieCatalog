package com.example.domain.feature_profile_screen.usecase

import com.example.domain.common.usecase.RemoveTokenUseCase
import com.example.domain.common.usecase.RemoveUserUseCase
import com.example.domain.feature_profile_screen.repository.ProfileRepository

class LogoutUseCase(
    private val repository: ProfileRepository,
    private val removeUserUseCase: RemoveUserUseCase,
    private val removeTokenUseCase: RemoveTokenUseCase
) {
    suspend operator fun invoke() {
        repository.logout()
        removeTokenUseCase()
        removeTokenUseCase()
    }
}