package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.navigationexample.presentation.navigation.Routs


@Composable
fun LaunchScreen(
    mainNavController: NavHostController,
    viewModelApartment: ApartmentViewModel
) {

    Column {
        Button(onClick = {
            viewModelApartment.getAllApartments()
            mainNavController.navigate(Routs.allApartmentsScreen)
        }) {
            Text(text = "Start")
        }
    }
}