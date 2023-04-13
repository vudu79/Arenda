package com.example.navigationexample.presentation.navigation.batton_navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.presentation.screens.AppatmentViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppatmentFinanceScreen(
    mainNavController: NavHostController,
    viewModel: AppatmentViewModel,
    appatment_name: String
) {
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
                viewModel = viewModel,
                appatment_name=appatment_name

            )
        }
    }
}