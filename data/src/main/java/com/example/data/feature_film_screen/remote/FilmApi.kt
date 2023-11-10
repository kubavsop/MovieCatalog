package com.example.data.feature_film_screen.remote

import com.example.data.common.dto.MovieDetailsDto
import com.example.data.common.dto.ReviewModifyDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path


interface FilmApi {

    @POST("movie/{movieId}/review/add")
    suspend fun addMovieReview(@Body reviewModifyDto: ReviewModifyDto, @Path("movieId") movieId: String)

    @PUT("movie/{movieId}/review/{id}/edit")
    suspend fun editMovieReview(
        @Body reviewModifyDto: ReviewModifyDto,
        @Path("movieId") movieId: String,
        @Path("id") id: String
    )

    @DELETE("movie/{movieId}/review/{id}/delete")
    suspend fun deleteMovieReview(
        @Path("movieId") movieId: String,
        @Path("id") id: String
    )

    @GET("movies/details/{id}")
    suspend fun getMovieDetailsById(@Path("id") id: String): MovieDetailsDto

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/"
    }
}