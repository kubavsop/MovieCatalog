package com.example.domain.common.repository

import com.example.domain.common.model.TokenResponse
import com.example.domain.common.model.User

interface UserRepository {
    fun getTokenResponse(): TokenResponse?

    fun saveTokenResponse(tokenResponse: TokenResponse)

    fun saveUser(user: User)
    fun getUser(): User?
}