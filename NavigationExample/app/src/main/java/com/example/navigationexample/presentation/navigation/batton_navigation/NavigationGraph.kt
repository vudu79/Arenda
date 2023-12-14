package com.example.navigationexample.presentation.navigation.batton_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigationexample.presentation.screens.*
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.BalanceViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel

@Composable
fun NavigationGraph(
    mainNavController: NavHostController,
    clientNavController: NavHostController,
    viewModelApartment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelBalance: BalanceViewModel,
    appatmentName: String
) {
    NavHost(clientNavController, startDestination = BottomNavItems.Clients.screen_route) {
        composable(BottomNavItems.Clients.screen_route) {
            ClientsScreen(
                mainNavController = mainNavController,
                viewModelClient = viewModelClient,
                viewModelAppatment = viewModelApartment,
                viewModelCalendar = viewModelCalendar,
                appatmentName = appatmentName
            )
        }
        composable(BottomNavItems.Calendar.screen_route) {
            CalendarScreen(
                navController = mainNavController,
                viewModelCalendar = viewModelCalendar,
                viewModelClient = viewModelClient,
                appatmentName = appatmentName
            )
        }
        composable(BottomNavItems.Ballance.screen_route) {
            viewModelBalance.getAllApartments()
            ApartmentBalanceScreen(
//                navController = mainNavController,
//                viewModelClient = viewModelClient,
//                viewModelCalendar = viewModelCalendar,
                apartmentName = appatmentName,
                viewModelApartment = viewModelApartment,
                viewModelBalance = viewModelBalance,
                mainNavController = mainNavController
            )
        }
        composable(BottomNavItems.Appatments.screen_route) {
            AppartmentsScreen(
                mainNavController = mainNavController,
                viewModelAppatment = viewModelApartment,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar
            )
        }
    }
}