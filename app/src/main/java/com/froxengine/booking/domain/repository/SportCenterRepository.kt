package com.froxengine.booking.domain.repository

import com.froxengine.booking.data.model.SportCenter

interface SportCenterRepository {
    suspend fun getSportCenters() : List<SportCenter>
}