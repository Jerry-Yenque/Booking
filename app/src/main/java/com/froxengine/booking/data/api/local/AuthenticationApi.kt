package com.froxengine.booking.data.api.local

import com.froxengine.booking.data.model.AuthenticationDto
import com.froxengine.booking.data.model.AuthenticationResponseDto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationApi {
    @POST("/api/authenticate")
    fun authenticate(@Body dto: AuthenticationDto): Call<AuthenticationResponseDto>

    @POST("/api/refreshauth")
    fun refreshToken(@Body body: Map<String, String>): Call<AuthenticationResponseDto>
}