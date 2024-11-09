package com.froxengine.booking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun AppNavigation(homeViewModel: HomeViewModel, navController: NavHostController = rememberNavController()) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.HomeScreen.route
    ) {
        composable(route = AppScreen.HomeScreen.route) {
            HomeScreen(navController, homeViewModel)
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
            PaymentMethodsScreen( homeViewModel.orderState, homeViewModel.clientName, { navController.popBackStack() } ) {
                navController.navigate(AppScreen.PaymentQrScreen.route)
            }
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