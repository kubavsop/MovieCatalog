package com.example.data.feature_main_screen.remote

import com.example.data.common.dto.MovieDetailsDto
import com.example.data.common.dto.MoviesPagedListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {
    @GET("{page}")
    suspend fun getMoviesByPage(@Path("page") page: Int): MoviesPagedListDto

    @GET("details/{id}")
    suspend fun getMovieDetailsById(@Path("id") id: String): MovieDetailsDto

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/movies/"
    }
}