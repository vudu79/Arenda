package com.example.navigationexample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexample.presentation.navigation.batton_navigation.AppatmentFinanceScreen
import com.example.navigationexample.presentation.screens.AddAppatmentScreen
import com.example.navigationexample.presentation.screens.AddClientScreen
import com.example.navigationexample.presentation.screens.AppatmentViewModel
import com.example.navigationexample.presentation.screens.MainScreen


@Composable
fun NavHostView(viewModel: AppatmentViewModel) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routs.home) {
        composable(Routs.home) {
            MainScreen(mainNavController, viewModel)
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(
                navController = mainNavController,
                viewModel,
                onHome = { mainNavController.navigate(Routs.home) })

        }

        composable(
            route = "${Routs.mainScreenClients}?appatment_name={appatment_name}",
            arguments = listOf(
                navArgument("appatment_name") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("appatment_name")
            appatment_name?.let {
                AppatmentFinanceScreen(mainNavController, viewModel, appatment_name)
            }
        }

        composable(
            route = "${Routs.addClientScreen}?appatment_name={appatment_name}",
            arguments = listOf(
                navArgument("appatment_name") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("appatment_name")
            appatment_name?.let {
                AddClientScreen(mainNavController, viewModel, appatment_name)
            }
        }





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
