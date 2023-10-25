package com.example.data.feature_main_screen.local.converter

import androidx.room.TypeConverter

class GenreListConverter {
    @TypeConverter
    fun fromGenreList(genreList: List<String>): String {
        return genreList.joinToString(",")
    }

    @TypeConverter
    fun toGenreList(genreString: String): List<String> {
        return genreString.split(",")
    }
}