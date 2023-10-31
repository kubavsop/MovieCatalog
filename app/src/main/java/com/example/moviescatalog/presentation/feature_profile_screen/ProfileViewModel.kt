package com.example.moviescatalog.presentation.feature_profile_screen

import androidx.lifecycle.ViewModel
import com.example.domain.feature_profile_screen.usecase.ChangeProfileUseCase
import com.example.domain.feature_profile_screen.usecase.GetProfileUseCase
import com.example.domain.feature_profile_screen.usecase.LogoutUseCase
import com.example.domain.model.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val changeProfileUseCase: ChangeProfileUseCase,
    private val getProfileUseCase: GetProfileUseCase,
    private val logoutUseCase: LogoutUseCase
): ViewModel() {

    suspend fun getProfileTest(): Profile {
        return getProfileUseCase()
    }
}