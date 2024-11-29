package com.froxengine.booking.presentation.navigation

sealed class AppScreen(val route: String) {
    data object HomeScreen: AppScreen("home")
    data object DetailScreen: AppScreen("detail")
    data object RegisterClientScreen: AppScreen("registerClient")
    data object PaymentQrScreen: AppScreen("payment_qr")
    data object PaymentsScreen: AppScreen("payments_screen")
    data object SettingsScreen: AppScreen("settings_screen")
    data object CardPaymentScreen: AppScreen("card_screen")
}
