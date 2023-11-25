package com.example.navigationexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.screens.AppatmentViewModel
import com.example.navigationexample.presentation.screens.BalanceViewModel
import com.example.navigationexample.presentation.screens.CalendarViewModel
import com.example.navigationexample.presentation.screens.ClientViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModelAppat: AppatmentViewModel by viewModels()
    private val viewModelClient: ClientViewModel by viewModels()
    private val viewModelCalendar: CalendarViewModel by viewModels()
    private val balanceViewModel: BalanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

//            val owner = LocalViewModelStoreOwner.current
//            owner?.let {
//                val viewModel: AppatmentViewModel = viewModel(
//                    it,
//                    "MainViewModel",
//                    AppatmentViewModelFactory(
//                        LocalContext.current.applicationContext
//                                as Application
//                    )
//                )
//            }
            NavHostView(
                viewModelAppatment = viewModelAppat,
                viewModelClient = viewModelClient,
                viewModelCalendar = viewModelCalendar,
                balanceViewModel =balanceViewModel
                )
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