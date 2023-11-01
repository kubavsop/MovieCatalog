package com.example.domain.feature_profile_screen.repository

import com.example.domain.model.Profile

interface ProfileRepository {
    suspend fun getProfile(): Profile

    suspend fun changeProfile(profile: Profile)

    suspend fun logout()
}