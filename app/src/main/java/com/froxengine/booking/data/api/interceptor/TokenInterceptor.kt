package com.froxengine.booking.data.api.interceptor

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.froxengine.booking.core.RetrofitProvider
import com.froxengine.booking.data.api.local.AuthenticationApi
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenInterceptor(private val context: Context) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        Log.e("Booking", "TOKEN INTERCEPTOR ARRIVED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
        Log.e("Booking", "Token in Auth.refreshtoken: ${AuthInterceptor.accessToken}")
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)

        val refreshToken = if (AuthInterceptor.refreshToken != "") {
            // If token dead
            Log.e("Booking", "Getting Token in Auth.refreshtoken: ${AuthInterceptor.refreshToken}")
            AuthInterceptor.refreshToken
        } else {
            // If app dead
            val refreshToken = preferences.getString("refreshToken", "'Couldn't get refresh token'").toString()
            val accessToken = preferences.getString("accessToken", "'Couldn't get refresh token'").toString()
            Log.e("Booking", "Tokens obtained from preferences, access : $accessToken, refresh: $refreshToken")
            AuthInterceptor.accessToken = accessToken
            AuthInterceptor.refreshToken = refreshToken
            refreshToken
        }

//         Evitar múltiples intentos de autenticación
        if (responseCount(response) >= 3) {
            return null
        }

        // Intentar refrescar el token
        val newToken = getUpdatedToken(refreshToken, preferences = preferences)

        return if (newToken != null) {
            setSetting()
            // Crear una nueva solicitud con el nuevo token de acceso
            response.request.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
        } else {
            null
        }
    }
    private fun setSetting() {
    }

    private fun responseCount(response: Response): Int {
        var count = 1
        var priorResponse: Response? = response.priorResponse
        while (priorResponse != null) {
            count++
            priorResponse = priorResponse.priorResponse
        }
        return count
    }

    private fun getUpdatedToken(refreshToken: String, preferences: SharedPreferences): String? {
        Log.e("Booking", "Starting updating token")
        val retrofit = RetrofitProvider.getClient(context, false)

        val authService = retrofit.create(AuthenticationApi::class.java)

        val body = mapOf("refreshToken" to refreshToken)
        val response = authService.refreshToken(body).execute()

        return if (response.isSuccessful) {
            val newAccessToken = response.body()?.accessToken
            AuthInterceptor.accessToken = newAccessToken!!
            AuthInterceptor.refreshToken = response.body()!!.refreshToken
            val editor = preferences.edit()
            editor.putString("accessToken", newAccessToken)
            editor.putString("refreshToken", response.body()!!.refreshToken)
            editor.apply()
            Log.e("Booking", "Returning successful!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            return AuthInterceptor.accessToken
        } else {
//            val intent = Intent(context, LoginActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            context.startActivity(intent)
            Log.e("Booking", "TOKEN INTERCEPTOR FAILED!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            null
        }
    }
}