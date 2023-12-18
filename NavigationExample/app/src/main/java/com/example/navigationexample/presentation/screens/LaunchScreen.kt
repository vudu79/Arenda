package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.utils.bottomBorder
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel


@Composable
fun LaunchScreen(
    mainNavController: NavHostController,
    viewModelApartment: ApartmentViewModel
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(androidx.compose.material3.MaterialTheme.colorScheme.primaryContainer)
    ) {
        Button(onClick = {
            viewModelApartment.getAllApartments()
            mainNavController.navigate(Routs.allApartmentsScreen)
        }) {
            Text(text = "Start")
        }
    }
}