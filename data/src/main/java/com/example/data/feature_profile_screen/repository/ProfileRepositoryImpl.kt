package com.example.data.feature_profile_screen.repository

import com.example.data.common.mapper.toProfile
import com.example.data.common.mapper.toProfileDto
import com.example.data.feature_profile_screen.remote.ProfileApi
import com.example.domain.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi
): ProfileRepository {
    override suspend fun getProfile(): Profile {
        return profileApi.getProfile().toProfile()
    }

    override suspend fun changeProfile(profile: Profile) {
        profileApi.changeProfile(profile.toProfileDto())
    }

    override suspend fun logout() {
        profileApi.logout()
    }
}