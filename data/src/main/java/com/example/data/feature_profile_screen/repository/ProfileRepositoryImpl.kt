package com.example.data.feature_profile_screen.repository

import com.example.data.common.mapper.toProfile
import com.example.data.common.mapper.toProfileDto
import com.example.data.feature_profile_screen.remote.ProfileApi
import com.example.domain.model.Profile
import com.example.domain.feature_profile_screen.repository.ProfileRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class ProfileRepositoryImpl(
    private val profileApi: ProfileApi,
    private val ioDispatcher: CoroutineDispatcher
) : ProfileRepository {
    override suspend fun getProfile(): Profile {
        return withContext(ioDispatcher) {
            profileApi.getProfile().toProfile()
        }
    }

    override suspend fun changeProfile(profile: Profile) {
        withContext(ioDispatcher) {
            profileApi.changeProfile(profile.toProfileDto())
        }
    }

    override suspend fun logout() {
        withContext(ioDispatcher) {
            profileApi.logout()
        }
    }
}