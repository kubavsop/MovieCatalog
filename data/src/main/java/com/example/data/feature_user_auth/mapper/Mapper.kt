package com.example.data.feature_user_auth.mapper

import com.example.data.feature_user_auth.remote.dto.LoginRequestDto
import com.example.data.feature_user_auth.remote.dto.ProfileDto
import com.example.data.feature_user_auth.remote.dto.TokenResponseDto
import com.example.domain.feature_user_auth.model.LoginRequest
import com.example.domain.feature_user_auth.model.Profile
import com.example.domain.feature_user_auth.model.TokenResponse


fun Profile.toProfileDto() = ProfileDto(
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