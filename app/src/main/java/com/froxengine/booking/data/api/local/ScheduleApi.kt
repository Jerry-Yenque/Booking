package com.froxengine.booking.data.api.local

import com.froxengine.booking.core.RetrofitProvider
import com.froxengine.booking.data.model.Schedule
import retrofit2.http.GET
import retrofit2.http.Query

interface ScheduleApi {
    @GET("api/schedules")
    suspend fun getSchedulesByCenterId(
        @Query("sportCenterId") sportCenterId: String
    ): Schedule
}

object ScheduleService {
    val retrofit : ScheduleApi by lazy {
        RetrofitProvider.getRetrofit().create(ScheduleApi::class.java)
    }
}