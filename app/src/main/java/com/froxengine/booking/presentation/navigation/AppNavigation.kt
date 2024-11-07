package com.froxengine.booking.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.froxengine.booking.presentation.screens.client.RegisterClientPreview
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
            RegisterClientScreen( homeViewModel.clientName, homeViewModel.isLoading) { doc, type ->
                homeViewModel.search(doc, type)
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