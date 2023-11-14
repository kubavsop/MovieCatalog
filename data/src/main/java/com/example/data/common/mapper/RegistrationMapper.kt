package com.example.data.common.mapper

import com.example.data.common.local.TokenResponseEntity
import com.example.data.common.dto.LoginRequestDto
import com.example.data.common.dto.UserRegistrationDto
import com.example.data.common.dto.TokenResponseDto
import com.example.data.common.local.UserEntity
import com.example.domain.common.model.LoginRequest
import com.example.domain.common.model.UserRegistration
import com.example.domain.common.model.TokenResponse
import com.example.domain.common.model.User
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