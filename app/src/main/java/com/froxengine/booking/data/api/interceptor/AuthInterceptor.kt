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
        var accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJBRE1JTiJdLCJzdWIiOiJLaW9zY28iLCJpYXQiOjE3MzI4NjQzNDcsImV4cCI6MTczMjk2NDM0N30.x5KRrFs_8fxGuJJVrWMTv4IW8DVmivERwwBViiNjphP_6lUPyiDZwlJ6W9UUpEW6RQIzZN8pX059mHDTyh3plw"
        var refreshToken: String = "dmv0fjez0YGiL02Jl5K8Nte1W3ks7EyhjNvit4wbw1WhYIiqW21CXOHhGB7vQDB9GfEqYNUEIgKME7JRPwjW9p0AuDbwhJo13PpzXBmEqwA04b6dssJgiHtKR9oYMQy0"
    }
}