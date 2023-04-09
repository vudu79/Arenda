package com.example.navigationexample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.presentation.screens.AddAppatmentScreen
import com.example.navigationexample.presentation.screens.MainScreen


@Composable
fun NavHostView() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Routs.home) {
        composable(Routs.home) {
            MainScreen(navController)
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(navController=navController, onHome = { navController.navigate(Routs.home) })

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
