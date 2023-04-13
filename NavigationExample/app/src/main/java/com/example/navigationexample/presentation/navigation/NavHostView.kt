package com.example.navigationexample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.presentation.navigation.batton_navigation.AppatmentFinanceView
import com.example.navigationexample.presentation.screens.AddAppatmentScreen
import com.example.navigationexample.presentation.screens.AppatmentViewModel
import com.example.navigationexample.presentation.screens.MainScreen


@Composable
fun NavHostView( viewModel: AppatmentViewModel) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routs.home) {
        composable(Routs.home) {
            MainScreen(mainNavController, viewModel)
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(navController=mainNavController, viewModel, onHome = { mainNavController.navigate(Routs.home) })

        }

        composable(Routs.mainScreenClients) {
            AppatmentFinanceView(mainNavController, viewModel)
        }
//
//        composable("settings") {
//            SettingsScreen(
//                onHome = { navController.popBackStack() },
//                onProfile = { navController.navigate("profile") }
//            )
//        }
//        composable("profile") {
//            ProfileScreen { navController.popBackStack("home", false) }
//        }
    }
}
