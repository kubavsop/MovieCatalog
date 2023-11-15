package com.example.data.feature_main_screen.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieElementEntity (
    @PrimaryKey
    val id: String,
    val page: Int,
    val country: String,
    val genres: List<String>,
    val userRating: Int?,
    val averageRating: Double,
    val name: String,
    val poster: String,
    val year: Int
)