package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.common.model.UserRegistration
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.common.model.User
import com.example.domain.common.usecase.SaveTokenUseCase
import com.example.domain.common.usecase.SaveUserUseCase
import java.text.SimpleDateFormat

class RegisterUserUseCase(
    private val repository: UserAuthRepository,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveTokenUseCase: SaveTokenUseCase,
    private val saveUserUseCase: SaveUserUseCase
) {
    suspend operator fun invoke(user: UserRegistration) {
        val inputFormat = SimpleDateFormat(INPUT_FORMAT)
        val outputFormat = SimpleDateFormat(OUTPUT_FORMAT)
        val date = inputFormat.parse(user.birthDate)
        val modifiedFormatProfile = user.copy(
            birthDate = outputFormat.format(date)
        )
        val token = repository.register(modifiedFormatProfile)
        saveTokenUseCase(token)
        val profile = getProfileUseCase()
        saveUserUseCase(User(id = profile.id))
    }
    private companion object {
        const val INPUT_FORMAT = "dd.MM.yyyy"
        const val OUTPUT_FORMAT = "yyyy-MM-dd"
    }
}