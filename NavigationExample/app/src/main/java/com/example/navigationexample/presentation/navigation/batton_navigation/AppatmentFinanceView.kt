package com.example.navigationexample.presentation.navigation.batton_navigation

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.navigationexample.presentation.screens.AppatmentViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AppatmentFinanceView(mainNavController: NavHostController, viewModel: AppatmentViewModel){
    val clientNavController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigation(navController = clientNavController) }
    ) {
        NavigationGraph(mainNavController = mainNavController, clientNavController = clientNavController, viewModel = viewModel)
    }
}