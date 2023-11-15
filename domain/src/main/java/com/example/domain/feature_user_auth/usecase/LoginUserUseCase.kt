package com.example.domain.feature_user_auth.usecase

import com.example.domain.common.model.LoginRequest
import com.example.domain.common.model.User
import com.example.domain.common.usecase.SaveTokenUseCase
import com.example.domain.common.usecase.SaveUserUseCase
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository

class LoginUserUseCase(
    private val repository: UserAuthRepository,
    private val getProfileUseCase: GetProfileUseCase,
    private val saveUserUseCase: SaveUserUseCase,
    private val saveTokenUseCase: SaveTokenUseCase
) {
    suspend operator fun invoke(loginRequest: LoginRequest) {
        val token = repository.login(loginRequest)
        saveTokenUseCase(token)
        val profile = getProfileUseCase()
        saveUserUseCase(User(id = profile.id))
    }
}