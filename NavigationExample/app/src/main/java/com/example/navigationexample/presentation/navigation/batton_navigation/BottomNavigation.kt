package com.example.navigationexample.presentation.navigation.batton_navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState


@Composable
fun BottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavItems.Clients,
        BottomNavItems.Calendar,
        BottomNavItems.Ballance,
        BottomNavItems.Appatments,
    )
    androidx.compose.material.BottomNavigation(

        backgroundColor = Color(223, 75, 0).copy(0.7f),
        contentColor = Color.White,
        elevation = 5.dp,
        modifier = Modifier
            .fillMaxHeight(.06f)
            .padding(start = 10.dp, end = 10.dp)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = { },
//                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title) },

                label = {
                    Text(
                        text = item.title,
                        fontSize = 12.sp
                    )
                },
                selectedContentColor = Color.White,
                unselectedContentColor = Color.White.copy(0.5f),
                alwaysShowLabel = true,
                selected = currentRoute == item.screen_route,
                onClick = {
                    navController.navigate(item.screen_route) {

                        navController.graph.startDestinationRoute?.let { screen_route ->
                            popUpTo(screen_route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}