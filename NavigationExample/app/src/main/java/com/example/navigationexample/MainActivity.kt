package com.example.navigationexample

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.screens.AppatmentViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val owner = LocalViewModelStoreOwner.current

            owner?.let {
                val viewModel: AppatmentViewModel = viewModel(
                    it,
                    "MainViewModel",
                    AppatmentViewModelFactory(
                        LocalContext.current.applicationContext
                                as Application
                    )
                )



                NavHostView(viewModel)
            }
        }
    }
}

//
//@Composable
//fun HomeScreen(navController: NavHostController) {
//    Column(
//        Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Home Screen")
//        Button(onClick = { navController.navigate("settings") }) {
//            Text("Go to Settings")
//        }
//        Button(onClick = { navController.navigate("about") }) {
//            Text("Go to About")
//        }
//    }
//}
//
//@Composable
//fun SettingsScreen(onHome: () -> Unit, onProfile: () -> Unit) {
//    Column(
//        Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Text("Settings Screen")
//        Button(onClick = onHome) { Text("Go back to Home") }
//        Button(onClick = onProfile) { Text("Go to Profile") }
//    }
//}
//
//@Composable
//fun ProfileScreen(onHome: () -> Unit) {
//    Column(
//        Modifier.fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
//    ) {
//        Button(onClick = onHome) {
//            Text("Go back to Home")
//        }
//    }
//}