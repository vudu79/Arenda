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
    apartmentName: String
) {
    NavHost(clientNavController, startDestination = BottomNavItems.Clients.screenRoute) {
        composable(BottomNavItems.Clients.screenRoute) {
            ClientsScreen(
                mainNavController = mainNavController,
                viewModelClient = viewModelClient,
                viewModelAppatment = viewModelApartment,
                viewModelCalendar = viewModelCalendar,
                appatmentName = apartmentName
            )
        }
        composable(BottomNavItems.Calendar.screenRoute) {
            CalendarScreen(
                navController = mainNavController,
                viewModelCalendar = viewModelCalendar,
                viewModelClient = viewModelClient,
                appatmentName = apartmentName
            )
        }
        composable(BottomNavItems.Ballance.screenRoute) {
            viewModelBalance.getAllApartments()
            ApartmentBalanceScreen(
//                navController = mainNavController,
//                viewModelClient = viewModelClient,
//                viewModelCalendar = viewModelCalendar,
                apartmentName = apartmentName,
                viewModelApartment = viewModelApartment,
                viewModelBalance = viewModelBalance,
                mainNavController = mainNavController
            )
        }
        composable(BottomNavItems.Appatments.screenRoute) {
            AppartmentsScreen(
                mainNavController = mainNavController,
                viewModelAppatment = viewModelApartment,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar
            )
        }
    }
}