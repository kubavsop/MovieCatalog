package com.example.domain.feature_user_auth.usecase

import com.example.domain.model.UserRegistration
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import java.text.SimpleDateFormat

class RegisterUserUseCase(
    private val repository: UserAuthRepository
) {
    suspend operator fun invoke(user: UserRegistration) {
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val date = inputFormat.parse(user.birthDate)
        val modifiedFormatProfile = user.copy(
            birthDate = outputFormat.format(date)
        )
        repository.register(modifiedFormatProfile)
    }
    private companion object {
        const val INPUT_FORMAT = "dd.MM.yyyy"
        const val OUTPUT_FORMAT = "yyyy-MM-dd"
    }
}