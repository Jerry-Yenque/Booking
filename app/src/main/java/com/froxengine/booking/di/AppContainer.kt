package com.froxengine.booking.di

import com.froxengine.booking.data.repository.ScheduleRepositoryImpl
import com.froxengine.booking.data.repository.SportCenterRepositoryImpl
import com.froxengine.booking.domain.repository.ScheduleRepository
import com.froxengine.booking.domain.repository.SportCenterRepository

interface AppContainer {
    val sportCenterRepository: SportCenterRepository
    val scheduleRepository: ScheduleRepository
}

class DependenciesAppContainer : AppContainer {
    override val sportCenterRepository: SportCenterRepository by lazy {
        SportCenterRepositoryImpl()
    }

    override val scheduleRepository: ScheduleRepository by lazy {
        ScheduleRepositoryImpl()
    }

}