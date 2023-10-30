package com.example.data.feature_profile_screen.mapper

import com.example.data.feature_profile_screen.remote.dto.ProfileDto
import com.example.domain.feature_profile_screen.model.Profile

fun Profile.toProfileDto() = ProfileDto(
    avatarLink = avatarLink,
    birthDate = birthDate,
    email = email,
    gender = gender,
    id = id,
    name = name,
    nickName = nickName
)

fun ProfileDto.toProfile() = Profile(
    avatarLink = avatarLink,
    birthDate = birthDate,
    email = email,
    gender = gender,
    id = id,
    name = name,
    nickName = nickName
)