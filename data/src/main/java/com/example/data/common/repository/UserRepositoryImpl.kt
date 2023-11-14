package com.example.data.common.repository

import com.example.data.common.mapper.toTokenResponse
import com.example.data.common.mapper.toTokenResponseEntity
import com.example.data.common.mapper.toUser
import com.example.data.common.mapper.toUserEntity
import com.example.data.feature_user_auth.local.UserStorage
import com.example.domain.common.model.TokenResponse
import com.example.domain.common.model.User
import com.example.domain.common.repository.UserRepository

class UserRepositoryImpl(
    private val userStorage: UserStorage,
): UserRepository {
    override fun getTokenResponse(): TokenResponse? {
        return userStorage.getToken()?.toTokenResponse()
    }

    override fun saveTokenResponse(tokenResponse: TokenResponse) {
        userStorage.saveToken(tokenResponseEntity = tokenResponse.toTokenResponseEntity())
    }

    override fun saveUser(user: User) {
        userStorage.saveUser(user = user.toUserEntity())
    }

    override fun getUser(): User? {
        return userStorage.getUser()?.toUser()
    }
}