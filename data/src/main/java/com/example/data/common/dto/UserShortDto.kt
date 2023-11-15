package com.example.data.common.dto

import java.util.UUID

data class UserShortDto(
    val avatar: String?,
    val nickName: String?,
    val userId: UUID
)