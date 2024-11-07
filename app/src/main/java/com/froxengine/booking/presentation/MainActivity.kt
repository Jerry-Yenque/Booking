package com.froxengine.booking.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.froxengine.booking.data.repository.ContactService
import com.froxengine.booking.presentation.navigation.AppNavigation
import com.froxengine.booking.presentation.screens.home.HomeScreen
import com.froxengine.booking.presentation.screens.home.HomeViewModel
import com.froxengine.booking.presentation.ui.theme.BooKingTheme

class MainActivity : ComponentActivity() {
    private lateinit var homeViewModel: HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupServicesAndViewModel()
        setContent {
            BooKingTheme {
//                homeViewModel = viewModel(factory = HomeViewModel.Factory)
                AppNavigation(homeViewModel)
//                HomeScreen()
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Greeting(
//                        name = "Android",
//                        modifier = Modifier.padding(innerPadding)
//                    )
//                }
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

