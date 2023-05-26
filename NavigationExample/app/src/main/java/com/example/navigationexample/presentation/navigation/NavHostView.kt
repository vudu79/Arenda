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
fun NavHostView(
    viewModelAppatment: AppatmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel
) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routs.home) {
        composable(Routs.home) {
            MainScreen(
                mainNavController, viewModelAppatment = viewModelAppatment,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar
            )
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(
                navController = mainNavController,
                viewModelAppatment,
                onHome = { mainNavController.navigate(Routs.home) })

        }

        composable(
            route = "${Routs.mainScreenClients}/{apartment_name}",
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("apartment_name")
            appatment_name?.let {
                AppatmentFinanceScreen(
                    mainNavController = mainNavController,
                    viewModelAppatment = viewModelAppatment,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar,
                    appatmentName = appatment_name
                )
            }
        }

        composable(
            route = "${Routs.addClientScreen}/{apartment_name}"
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("apartment_name")
            appatment_name?.let {
                AddClientScreen(
                    navController = mainNavController,
                    viewModelClient = viewModelClient,
                    appatmentName = appatment_name
                )
            }
        }

        composable(
            route = "${Routs.setClientPeriod}/{apartment_name}",
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("apartment_name")
            appatment_name?.let {
                SetDatePeriodScreen(
                    navController = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar,
                    appatmentName = appatment_name
                )
            }
        }

        composable(
            route = "${Routs.clientDitailsScreen}/{client_phone}",
            arguments = listOf(navArgument("client_phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("client_phone")?.let { cp ->
                val clientPhone = cp
                ClientDitailsScreen(
                    mainNavController = mainNavController,
                    viewModelClient = viewModelClient,
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
