package com.froxengine.booking.data.repository

import com.froxengine.booking.data.api.local.ScheduleService
import com.froxengine.booking.data.model.Schedule
import com.froxengine.booking.domain.repository.ScheduleRepository
import kotlinx.coroutines.runBlocking

class ScheduleRepositoryImpl : ScheduleRepository {
    override suspend fun getSchedulesByCenterId(sportCenterId: String): Schedule = ScheduleService.retrofit.getSchedulesByCenterId(sportCenterId)

}

fun main() {
    val a = ScheduleRepositoryImpl()

    try {
        runBlocking {
            val result = a.getSchedulesByCenterId("670e001544d43338ca359146")
            println("Resultado : $result")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}