package com.example.navigationexample.presentation.navigation.batton_navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.navigationexample.presentation.screens.*
import com.kizitonwose.calendar.sample.compose.*

@Composable
fun NavigationGraph(
    mainNavController: NavHostController,
    clientNavController: NavHostController,
    viewModel: AppatmentViewModel,
    appatment_name: String
) {
    NavHost(clientNavController, startDestination = BottomNavItems.Clients.screen_route) {
        composable(BottomNavItems.Clients.screen_route) {
            ClientsScreen(mainNavController = mainNavController, viewModel = viewModel, appatmentName=appatment_name)
        }
        composable(BottomNavItems.Calendar.screen_route) {
            CalendarScreen(viewModel = viewModel)
        }
        composable(BottomNavItems.Ballance.screen_route) {
            BallanceScreen()
        }
        composable(BottomNavItems.Appatments.screen_route) {
            MainScreen(mainNavController = mainNavController, viewModel = viewModel)
        }
    }
}