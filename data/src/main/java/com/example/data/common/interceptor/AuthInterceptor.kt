package com.example.data.common.interceptor

import com.example.domain.common.usecase.GetTokenUseCase
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val getTokenUseCase: GetTokenUseCase
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (request.header(AUTHORIZATION_HEADER) == null) {
            getTokenUseCase()?.let{
                builder.addHeader(
                    AUTHORIZATION_HEADER,
                    "$BEARER ${it.token}"
                )
            }
        }

        return chain.proceed(builder.build())
    }

    private companion object {
        const val BEARER = "BEARER"
        const val AUTHORIZATION_HEADER = "Authorization"
    }
}