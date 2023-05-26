package com.example.navigationexample.presentation.navigation.batton_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigationexample.presentation.screens.*

@Composable
fun NavigationGraph(
    mainNavController: NavHostController,
    clientNavController: NavHostController,
    viewModelAppatment: AppatmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    appatmentName: String
) {
    NavHost(clientNavController, startDestination = BottomNavItems.Clients.screen_route) {
        composable(BottomNavItems.Clients.screen_route) {
            ClientsScreen(
                mainNavController = mainNavController,
                viewModelClient = viewModelClient,
                viewModelAppatment = viewModelAppatment,
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
            SetDatePeriodScreen(
                navController = mainNavController,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar,
                appatmentName = appatmentName
            )
        }
        composable(BottomNavItems.Appatments.screen_route) {
            MainScreen(
                mainNavController = mainNavController,
                viewModelAppatment = viewModelAppatment,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar
            )
        }
    }
}