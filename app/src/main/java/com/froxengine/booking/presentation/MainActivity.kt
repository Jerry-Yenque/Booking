package com.froxengine.booking.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.froxengine.booking.data.repository.ContactService
import com.froxengine.booking.presentation.navigation.AppNavigation
import com.froxengine.booking.presentation.screens.home.HomeViewModel
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

class MainActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupServicesAndViewModel()
        setContent {
            BooKingTheme {
                AppNavigation(homeViewModel)
            }
        }
    }

    private fun setupServicesAndViewModel() {
        homeViewModel = ViewModelProvider(
            this,
            HomeViewModel.Factory
        )[HomeViewModel::class.java]

        Intent(this, ContactService::class.java).also { intent ->
            bindService(intent, homeViewModel.contactServiceConnection, Context.BIND_AUTO_CREATE)
        }
    }
}


