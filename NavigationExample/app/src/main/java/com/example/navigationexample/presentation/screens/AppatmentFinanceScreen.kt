package com.example.navigationexample.presentation.navigation.batton_navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.BalanceViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ApartmentFinanceScreen(
    mainNavController: NavHostController,
    viewModelApartment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelBalance: BalanceViewModel,
    apartmentName: String
) {
    val currentApartment = viewModelApartment.currentApartment
    val clientNavController = rememberNavController()
    val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    if (currentRoute != BottomNavItems.Appatments.screen_route) {
        Scaffold(
            bottomBar = {
                if (clientNavController.currentBackStackEntryAsState().value?.destination?.route != BottomNavItems.Appatments.screen_route) {
                    BottomNavigation(navController = clientNavController)
                }
            }
        ) {
            NavigationGraph(
                mainNavController = mainNavController,
                clientNavController = clientNavController,
                viewModelApartment = viewModelApartment,
                viewModelClient = viewModelClient,
                viewModelCalendar =viewModelCalendar,
                viewModelBalance = viewModelBalance,
                appatmentName=currentApartment.value!!.name
            )
        }
    }
}