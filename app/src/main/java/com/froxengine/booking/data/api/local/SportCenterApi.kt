package com.froxengine.booking.data.api.local

import com.froxengine.booking.core.RetrofitProvider
import com.froxengine.booking.data.model.SportCenter
import retrofit2.http.GET

interface SportCenterApi {
    @GET("api/sportCenters")
    suspend fun getSportCenters() : List<SportCenter>
}

object SportCenterService {
    val retrofit : SportCenterApi by lazy {
        RetrofitProvider.getRetrofit(includeLoggingInterceptor=false).create(SportCenterApi::class.java)
    }
}