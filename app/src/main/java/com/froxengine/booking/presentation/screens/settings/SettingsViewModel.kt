package com.froxengine.booking.presentation.screens.settings

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import com.froxengine.booking.data.api.local.SportCenterService


class SettingsViewModel(application: Application) : AndroidViewModel(application) {

//    private val appContainer = (application as ZenApplication).container

    var serverHost by mutableStateOf("")
        private set

    init {
        loadBaseUrl()
    }

    private fun loadBaseUrl() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        serverHost = sharedPreferences.getString("SERVER_HOST", "http://192.168.x.x:4000").toString()
    }

    fun saveBaseUrl() {
        val sharedPreferences = getApplication<Application>().getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("SERVER_HOST", serverHost)
        editor.apply()
        Log.d("ZEN", "UPDATING RETRO")
        SportCenterService.reloadRetrofit()
//        _baseUrl.value = newBaseUrl

        // Update the base URL in the container
//        appContainer.updateBaseUrl(_baseUrl.value!!)
    }

    /**
     * To update the input state in IU, every key press it will update serverHost
     */
    fun updateBaseUrl(newServerHost: String) {
        serverHost = newServerHost
    }
}