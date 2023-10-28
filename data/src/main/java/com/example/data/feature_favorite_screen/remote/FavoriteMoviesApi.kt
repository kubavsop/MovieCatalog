package com.example.data.feature_favorite_screen.remote

import retrofit2.http.DELETE
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @DELETE("{id}/delete")
    suspend fun deleteFavoriteMovie(@Path("id") id: String)

    @DELETE("{id}/add")
    suspend fun addFavoriteMovie(@Path("id") id: String)

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/favorites/"
    }
}