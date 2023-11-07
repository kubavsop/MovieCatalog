package com.example.data.feature_favorite_screen.remote

import com.example.data.common.dto.MovieElementsResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FavoriteMoviesApi {

    @GET("favorites")
    suspend fun getFavoriteMovies(): MovieElementsResponse

    @DELETE("favorites/{id}/delete")
    suspend fun deleteFavoriteMovie(@Path("id") id: String)

    @POST("favorites/{id}/add")
    suspend fun addFavoriteMovie(@Path("id") id: String)

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/"
    }
}