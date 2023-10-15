package com.example.data.remote

import com.example.data.remote.dto.LoginRequestDto
import com.example.data.remote.dto.ProfileDto
import com.example.data.remote.dto.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST


interface UserAuthApi {
    @POST("register")
    suspend fun register(@Body profile: ProfileDto): TokenResponseDto


    @POST("login")
    suspend fun login(@Body loginRequest: LoginRequestDto): TokenResponseDto

    companion object {
        const val BASE_URL = "https://react-midterm.kreosoft.space/api/account/"
    }
}