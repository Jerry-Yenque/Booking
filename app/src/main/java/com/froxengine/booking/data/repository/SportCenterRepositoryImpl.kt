package com.froxengine.booking.data.repository

import com.froxengine.booking.data.api.local.SportCenterService
import com.froxengine.booking.data.model.SportCenter
import com.froxengine.booking.domain.repository.SportCenterRepository
import kotlinx.coroutines.runBlocking

class SportCenterRepositoryImpl : SportCenterRepository {

    override suspend fun getSportCenters(): List<SportCenter> = SportCenterService.retrofit.getSportCenters()
}


fun main() {
    val a = SportCenterRepositoryImpl()

    try {
        runBlocking {
            val result = a.getSportCenters()
            println("Resultado : ${result[0]}")
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}