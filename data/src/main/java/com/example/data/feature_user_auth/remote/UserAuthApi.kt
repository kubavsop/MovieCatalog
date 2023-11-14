package com.example.data.feature_user_auth.remote

import com.example.data.common.dto.LoginRequestDto
import com.example.data.common.dto.UserRegistrationDto
import com.example.data.common.dto.TokenResponseDto
import retrofit2.http.Body
import retrofit2.http.POST


//interface UserAuthApi {
//    @POST("account/register")
//    suspend fun register(@Body profile: UserRegistrationDto): TokenResponseDto
//
//
//    @POST("account/login")
//    suspend fun login(@Body loginRequest: LoginRequestDto): TokenResponseDto
//
//    companion object {
//        const val BASE_URL = "https://react-midterm.kreosoft.space/api/account/"
//    }
//}