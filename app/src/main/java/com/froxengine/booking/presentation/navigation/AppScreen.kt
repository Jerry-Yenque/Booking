package com.froxengine.booking.presentation.navigation

sealed class AppScreen(val route: String) {
    data object HomeScreen: AppScreen("home")
    data object DetailScreen: AppScreen("detail")
    data object RegisterClientScreen: AppScreen("registerClient")
}