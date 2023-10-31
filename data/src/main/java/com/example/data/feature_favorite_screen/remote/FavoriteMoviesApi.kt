package com.example.data.feature_favorite_screen.remote

import com.example.data.dto.MovieElementDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @GET
    suspend fun getFavoriteMovies(): List<MovieElementDto>

    @DELETE("{id}/delete")
    suspend fun deleteFavoriteMovie(@Path("id") id: String)

    @POST("{id}/add")
    suspend fun addFavoriteMovie(@Path("id") id: String)

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/favorites/"
    }
}