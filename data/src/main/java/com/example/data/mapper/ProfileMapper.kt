package com.example.data.mapper

import com.example.data.dto.ProfileDto
import com.example.domain.model.Profile

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