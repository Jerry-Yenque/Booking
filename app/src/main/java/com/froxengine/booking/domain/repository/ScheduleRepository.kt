package com.froxengine.booking.domain.repository

import com.froxengine.booking.data.model.Schedule

interface ScheduleRepository {
    suspend fun getSchedulesByCenterId(sportCenterId: String) : Schedule
}