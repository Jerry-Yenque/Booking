package com.froxengine.booking.data.api.interceptor

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request =
            request.newBuilder().header("Authorization", "Bearer $accessToken").build()

        return chain.proceed(request)
    }

    companion object {
        var accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJBRE1JTiJdLCJzdWIiOiJLaW9zY28iLCJpYXQiOjE3MzEwMzI1MDIsImV4cCI6MTczMTEzMjUwMn0.DyVKw48a7Em5p4dj6jYJAKV4Jv5ufi-3zae-P8Y1WDUQ69Ebb1B2BLbiHxq6hLTIzTsnN344OdQJTlYGa5ekuQ"
        var refreshToken: String = "UIQmXsuuTstOWnbyUdwPcuF7HmZ6NF7yWOPrKoLXmm7FNgGCs1cRJL0QgrhUhm51m7B9Ug9QZEm38b5FqBmTovzKMtrdcLgVyyRey6AVF2Lv4SQ4ASLbeGCWJAGby8sl"
    }
}