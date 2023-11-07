package com.example.data.feature_profile_screen.remote

import com.example.data.common.dto.ProfileDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ProfileApi {

    @GET("profile")
    suspend fun getProfile(): ProfileDto

    @PUT("profile")
    suspend fun changeProfile(@Body profileDto: ProfileDto)

    @POST("logout")
    suspend fun logout()

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/account/"
    }
}