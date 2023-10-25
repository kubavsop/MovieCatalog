package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_user_auth.model.Profile
import com.example.domain.feature_user_auth.model.TokenResponse
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import java.text.SimpleDateFormat

class RegisterUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend operator fun invoke(profile: Profile): TokenResponse {
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val date = inputFormat.parse(profile.birthDate)
        val modifiedFormatProfile = profile.copy(
            birthDate = outputFormat.format(date)
        )
        return repository.register(modifiedFormatProfile)
    }
    private companion object {
        const val INPUT_FORMAT = "dd.MM.yyyy"
        const val OUTPUT_FORMAT = "yyyy-MM-dd"
    }
}