package com.example.data.common.mapper

import com.example.data.feature_user_auth.local.TokenResponseEntity
import com.example.data.common.dto.LoginRequestDto
import com.example.data.common.dto.UserRegistrationDto
import com.example.data.common.dto.TokenResponseDto
import com.example.data.feature_user_auth.local.UserEntity
import com.example.domain.model.LoginRequest
import com.example.domain.model.UserRegistration
import com.example.domain.model.TokenResponse
import com.example.domain.model.User
import java.util.UUID


fun UserRegistration.toUserRegistrationDto() = UserRegistrationDto(
    userName = userName,
    name = name,
    password = password,
    email = email,
    birthDate = birthDate,
    gender = gender
)

fun LoginRequest.toLoginRequestDto() = LoginRequestDto(
    username = username,
    password = password
)

fun TokenResponseDto.toTokenResponse() = TokenResponse(
    token = token
)

fun TokenResponse.toTokenResponseEntity() = TokenResponseEntity(
    token = token
)

fun TokenResponseDto.toTokenResponseEntity() = TokenResponseEntity(
    token = token
)

fun TokenResponseEntity.toTokenResponse() = TokenResponse(
    token = token
)

fun User.toUserEntity() = UserEntity(
    id = id.toString()
)

fun UserEntity.toUser() = User(
    id = UUID.fromString(id)
)