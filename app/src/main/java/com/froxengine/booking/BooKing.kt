package com.froxengine.booking

import android.app.Application
import android.content.Context
import com.froxengine.booking.di.AppContainer
import com.froxengine.booking.di.DependenciesAppContainer

class BooKing : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        instance = this
        container = DependenciesAppContainer()
    }

    companion object {
        private var instance: BooKing? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}