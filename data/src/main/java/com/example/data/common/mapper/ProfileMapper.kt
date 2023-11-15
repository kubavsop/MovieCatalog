package com.example.data.common.mapper

import com.example.data.common.dto.ProfileDto
import com.example.domain.common.model.Profile

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