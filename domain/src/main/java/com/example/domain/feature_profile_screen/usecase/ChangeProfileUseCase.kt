package com.example.domain.feature_profile_screen.usecase

import com.example.domain.common.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import java.text.SimpleDateFormat

class ChangeProfileUseCase(
    private val repository: ProfileRepository
) {
    suspend operator fun invoke(profile: Profile) {
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val date = inputFormat.parse(profile.birthDate)
        val modifiedFormatProfile = profile.copy(
            birthDate = outputFormat.format(date)
        )
        repository.changeProfile(modifiedFormatProfile)
    }
    private companion object {
        const val INPUT_FORMAT = "dd.MM.yyyy"
        const val OUTPUT_FORMAT = "yyyy-MM-dd"
    }
}