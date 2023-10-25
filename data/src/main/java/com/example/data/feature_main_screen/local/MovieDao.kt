package com.example.data.feature_main_screen.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.feature_main_screen.local.entity.MovieElementEntity

@Dao
interface MovieDao {

    @Upsert
    suspend fun upsertAll(movies: List<MovieElementEntity>)

    @Query("SELECT * FROM movieelemententity")
    fun pagingSource(): PagingSource<Int, MovieElementEntity>

    @Query("DELETE FROM movieelemententity")
    suspend fun clearAll()
}