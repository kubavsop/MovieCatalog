package com.example.domain.feature_profile_screen.usecase

import com.example.domain.common.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import java.text.SimpleDateFormat

class GetProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(): Profile {
        val profile = repository.getProfile()
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val date = inputFormat.parse(profile.birthDate)
        return profile.copy(
            birthDate = outputFormat.format(date)
        )
    }

    private companion object {
        const val OUTPUT_FORMAT = "dd.MM.yyyy"
        const val INPUT_FORMAT = "yyyy-MM-dd"
    }
}
