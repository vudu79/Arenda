package com.example.navigationexample.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.navigationexample.presentation.navigation.batton_navigation.AppatmentFinanceScreen
import com.example.navigationexample.presentation.screens.*


@Composable
fun NavHostView(viewModelAppatment: AppatmentViewModel,
                viewModelClient: ClientViewModel,
) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routs.home) {
        composable(Routs.home) {
            MainScreen(mainNavController, viewModelAppatment)
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(
                navController = mainNavController,
                viewModelAppatment,
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
                AppatmentFinanceScreen(mainNavController, viewModelAppatment, appatment_name)
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
                AddClientScreen(mainNavController, viewModelAppatment, appatment_name)
            }
        }

        composable(
            route = "${Routs.setClientPeriod}?appatment_name={appatment_name}",
            arguments = listOf(
                navArgument("appatment_name") {
                    defaultValue = ""
                    type = NavType.StringType
                }
            )
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("appatment_name")
            appatment_name?.let {
                SetDatePeriodScreen(mainNavController, viewModelAppatment, appatment_name)
            }
        }

        composable(
            route = "${Routs.clientDitailsScreen}/{client_phone}",
            arguments = listOf (navArgument("client_phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("client_phone")?.let { client ->
                val clientPhone = client
                ClientDitailsScreen(
                    mainNavController = mainNavController,
                    viewModel = viewModelClient,
                    clientPhone = clientPhone
                )
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
