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
    viewModelApartment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelBalance: BalanceViewModel
) {
    val mainNavController = rememberNavController()
    NavHost(navController = mainNavController, startDestination = Routs.home) {
        composable(Routs.home) {
            LaunchScreen(
                mainNavController,
                viewModelApartment = viewModelApartment,
            )
        }

        composable(Routs.allApartmentsScreen) {
            AppartmentsScreen(
                mainNavController,
                viewModelAppatment = viewModelApartment,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar
            )
        }

        composable(Routs.addAppatmentScreen) {
            AddAppatmentScreen(
                navController = mainNavController,
                viewModel= viewModelApartment,
                onHome = { mainNavController.navigate(Routs.home) })

        }

        composable(
            route = "${Routs.mainScreenClients}/{apartment_name}",
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("apartment_name")
            appatment_name?.let {
                AppatmentFinanceScreen(
                    mainNavController = mainNavController,
                    viewModelApartment = viewModelApartment,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar,
                    appatmentName = appatment_name,
                    viewModelBalance = viewModelBalance
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
                    viewModelAppatment = viewModelApartment,
                    appatmentName = appatment_name
                )
            }
        }

        composable(
            route = "${Routs.setClientPeriodFromAddClient}/{apartment_name}",
        ) { navBackStackEntry ->
            val appatment_name = navBackStackEntry.arguments?.getString("apartment_name")
            appatment_name?.let {
                SetDatePeriodScreen(
                    navController = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar,
                    appatmentName = appatment_name,
                    clientPhone = ""
                )
            }
        }

        composable(
            route = "${Routs.setClientPeriodFromEditClient}/{client_phone}",
        ) { navBackStackEntry ->
            val clientPhone = navBackStackEntry.arguments?.getString("client_phone")
            clientPhone?.let {
                SetDatePeriodScreen(
                    navController = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar,
                    appatmentName = "",
                    clientPhone = clientPhone
                )
            }
        }

        composable(
            route = "${Routs.clientUpdateScreen}/{client_phone}",
            arguments = listOf(navArgument("client_phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("client_phone")?.let { cp ->
                val clientPhone = cp
                ClientUpdateScreen(
                    mainNavController = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelAppatment = viewModelApartment,
                    clientPhone = clientPhone
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
                    viewModelAppatment = viewModelApartment,
                    clientPhone = clientPhone
                )
            }
        }


        composable(
            route = "${Routs.clientPaymentScreen}/{client_phone}",
            arguments = listOf(navArgument("client_phone") { type = NavType.StringType })
        ) { backStackEntry ->
            val arguments = requireNotNull(backStackEntry.arguments)
            arguments.getString("client_phone")?.let { cp ->
                val clientPhone = cp

                ClientPaymentScreen(
                    mainNavController = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelAppatment = viewModelApartment,
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
