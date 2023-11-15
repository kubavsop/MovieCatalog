package com.example.data.common.remote

import com.example.data.common.dto.LoginRequestDto
import com.example.data.common.dto.MovieDetailsDto
import com.example.data.common.dto.MovieElementsResponse
import com.example.data.common.dto.MoviesPagedListDto
import com.example.data.common.dto.ProfileDto
import com.example.data.common.dto.ReviewModifyDto
import com.example.data.common.dto.TokenResponseDto
import com.example.data.common.dto.UserRegistrationDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface MovieCatalogApi {

    //Auth
    @POST("account/register")
    suspend fun register(@Body profile: UserRegistrationDto): TokenResponseDto

    @POST("account/login")
    suspend fun login(@Body loginRequest: LoginRequestDto): TokenResponseDto

    //Profile
    @GET("account/profile")
    suspend fun getProfile(): ProfileDto

    @PUT("account/profile")
    suspend fun changeProfile(@Body profileDto: ProfileDto)

    @POST("account/logout")
    suspend fun logout()

    //Movie
    @GET("movies/{page}")
    suspend fun getMoviesByPage(@Path("page") page: Int): MoviesPagedListDto

    @GET("movies/details/{id}")
    suspend fun getMovieDetailsById(@Path("id") id: String): MovieDetailsDto

    //Review
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

    //Favorite
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