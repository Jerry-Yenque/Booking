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
        var accessToken: String = "eyJhbGciOiJIUzUxMiJ9.eyJwZXJtaXNzaW9ucyI6WyJBRE1JTiJdLCJzdWIiOiJLaW9zY28iLCJpYXQiOjE3MzExMTE1NjgsImV4cCI6MTczMTIxMTU2OH0.Tkn4O9xPaDxs90IC9t5AmaqctwmNMXsFMIcawjRhYozhORteQ-RoL2dpRQULph5glNi4Pzdf3pPyb2LI5zrG7g"
        var refreshToken: String = "T4qCJwccFzSBseQuoUfWZMSK6SvbOh8TPzyK7GkKGQi7dZZBOIEKjtmWLhIZpBDKkdHwkObIPYgO2N5AffuftrIoKPPyaApcAqxHGmLTJQzKbYWru2Ak5fMjx8h2tkBK"
    }
}