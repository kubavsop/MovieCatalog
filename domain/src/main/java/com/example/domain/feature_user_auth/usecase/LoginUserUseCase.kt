package com.example.domain.feature_user_auth.usecase

import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.model.LoginRequest
import com.example.domain.feature_user_auth.repositroy.UserAuthRepository
import com.example.domain.model.User

class LoginUserUseCase(
    private val repository: UserAuthRepository,
    private val getProfileUseCase: GetProfileUseCase
) {
    suspend operator fun invoke(loginRequest: LoginRequest) {
        val token = repository.login(loginRequest)
        repository.saveTokenResponse(token)
        val profile = getProfileUseCase()
        repository.saveUser(User(id = profile.id))
    }
}