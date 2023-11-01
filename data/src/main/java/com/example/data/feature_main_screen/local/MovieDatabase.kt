package com.example.data.feature_main_screen.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.feature_main_screen.local.converter.GenreListConverter
import com.example.data.feature_main_screen.local.entity.MovieElementEntity

@Database(
    entities = [MovieElementEntity::class],
    version = 1
)
@TypeConverters(GenreListConverter::class)
abstract class MovieDatabase : RoomDatabase() {
    abstract val dao: MovieDao

    companion object {
        const val NAME = "movie.db"
    }
}