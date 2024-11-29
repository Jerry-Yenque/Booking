package com.froxengine.booking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.froxengine.booking.presentation.screens.PaymentMethodsScreen
import com.froxengine.booking.presentation.screens.PaymentQrScreen
import com.froxengine.booking.presentation.screens.client.RegisterClientScreen
import com.froxengine.booking.presentation.screens.detail.SportCenterReservationScreen
import com.froxengine.booking.presentation.screens.home.HomeScreen
import com.froxengine.booking.presentation.screens.home.HomeViewModel
import com.froxengine.booking.presentation.screens.payments.CardPayment
import com.froxengine.booking.presentation.screens.settings.SettingsScreen

@Composable
fun AppNavigation(homeViewModel: HomeViewModel = viewModel(), navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.HomeScreen.route
    ) {
        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(
                navigateSettings = { navController.navigate(AppScreen.SettingsScreen.route) },
                sportCenters = homeViewModel.sportCenter,
                sportCenterSelected = { homeViewModel.sportCenterSelected(it) },
                navigateDetails = { navController.navigate(AppScreen.DetailScreen.route)}
            )
        }
        composable(route = AppScreen.DetailScreen.route) {
            SportCenterReservationScreen(navController, homeViewModel)
        }
        composable(route = AppScreen.RegisterClientScreen.route) {
            RegisterClientScreen({ navController.navigate(AppScreen.PaymentsScreen.route) }, homeViewModel.orderState, homeViewModel.clientName, homeViewModel.isLoading) { doc, type ->
                homeViewModel.search(doc, type)
            }
        }
        composable(route = AppScreen.PaymentQrScreen.route) {
            val orderState by homeViewModel.orderState.collectAsState()
            PaymentQrScreen({
                homeViewModel.resetOrderState()
                navController.navigate(AppScreen.HomeScreen.route)
                            }, orderState , homeViewModel.clientName) { navController.popBackStack() }
        }
        composable(route= AppScreen.PaymentsScreen.route) {
            PaymentMethodsScreen( navigateCardPayment = {navController.navigate(AppScreen.CardPaymentScreen.route)}, homeViewModel.orderState, homeViewModel.clientName, { navController.popBackStack() } ) {
                navController.navigate(AppScreen.PaymentQrScreen.route)
            }
        }
        composable(route = AppScreen.SettingsScreen.route) {
            SettingsScreen({ homeViewModel.getSportCenters() })
        }
        composable(route = AppScreen.CardPaymentScreen.route) {
            CardPayment()
        }
//        composable(
//            route = AppScreen.DetailScreen.route,
//            arguments = listOf(navArgument("sportCenterId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val sportCenterId = backStackEntry.arguments?.getString("sportCenterId")
//            SportCenterReservationScreen(sportCenterId!!)
//        }
    }
}