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
        var accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJBRE1JTiJdLCJzdWIiOiJLaW9zY28iLCJpYXQiOjE3MzA4NjgxNjQsImV4cCI6MTczMDk2ODE2NH0.DfG1K1Hu04I0gHDDSyw4B3gz951uGdPgbNnV6zCtM7nPmlI1C27877ia0loCe_oAh-a728FNGDEBBElg21c19g"
        var refreshToken: String = "iVeNjR4IARyAQGLLNUz9Or88Lkafbc491A3dW4t2Tr8Yb2HotQdxDaVHDRM1mW0CAmBpHZwHHQMAu8ESpLKOmiTDelmNhOSWaIZjUOeoJKnAVwjDXWuftdiv4jlNwzY1"
    }
}