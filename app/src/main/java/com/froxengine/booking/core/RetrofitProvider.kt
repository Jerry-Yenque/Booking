package com.froxengine.booking.core

import android.content.Context
import android.util.Log
import com.froxengine.booking.BooKing.Companion.applicationContext
import com.froxengine.booking.data.api.interceptor.AuthInterceptor
import com.froxengine.booking.data.api.interceptor.TokenInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    fun getRetrofit(includeAuth: Boolean = false, includeLoggingInterceptor: Boolean = true): Retrofit { //context: Context
        val baseUrl = getBaseUrlFromPreferences() //context
        val loggingInterceptor = HttpLoggingInterceptor()
        if (includeLoggingInterceptor) loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBldr = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
        if (includeAuth) clientBldr.addInterceptor(AuthInterceptor())

        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .client(clientBldr.build())
            .build()
    }

    fun getClient(context: Context, includeAuth: Boolean, includeTokenInterceptor: Boolean = true): Retrofit {
        val baseUrl = getBaseUrlForSynerPOS()
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val clientBldr = OkHttpClient.Builder()

        clientBldr.addInterceptor(loggingInterceptor)

        if (includeTokenInterceptor) clientBldr.authenticator(TokenInterceptor(context))
        if (includeAuth) clientBldr.addInterceptor(AuthInterceptor())


        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(clientBldr.build())
            .build()
    }

    private fun getBaseUrlForSynerPOS(): String {
        return "http://192.168.1.17:8080"
    }
    private fun getBaseUrlFromPreferences(): String { //context: Context
//        val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        Log.d("BooKing","obteniendo preference")
//        val sharedPreferences = applicationContext().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
//        return sharedPreferences.getString("SERVER_HOST", "http://server.host:4000")!!
        return "http://192.168.1.17:4000"
//        return "http://10.0.2.2:4000"
//            "http://localhost:4000"
    }
}