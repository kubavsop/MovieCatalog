package com.example.data.mapper

import com.example.data.remote.dto.LoginRequestDto
import com.example.data.remote.dto.ProfileDto
import com.example.data.remote.dto.TokenResponseDto
import com.example.domain.model.LoginRequest
import com.example.domain.model.Profile
import com.example.domain.model.TokenResponse


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