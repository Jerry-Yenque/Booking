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
        var accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJBRE1JTiJdLCJzdWIiOiJLaW9zY28iLCJpYXQiOjE3MzE3MTg1NDAsImV4cCI6MTczMTgxODU0MH0.voBJ8zFnWoO8ZtD7PeVvd4eWELaZbSpndlR2Nr2ip4xuasYI2CcQ2uLSyUR637cpCjLckWJd5kGCICwp5DPFAQ"
        var refreshToken: String = "dmv0fjez0YGiL02Jl5K8Nte1W3ks7EyhjNvit4wbw1WhYIiqW21CXOHhGB7vQDB9GfEqYNUEIgKME7JRPwjW9p0AuDbwhJo13PpzXBmEqwA04b6dssJgiHtKR9oYMQy0"
    }
}