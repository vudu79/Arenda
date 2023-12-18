package com.example.navigationexample.presentation.navigation.batton_navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.navigationexample.R


sealed class BottomNavItems(var title:String, var icon:ImageVector, var screen_route:String){

    object Clients : BottomNavItems("Клиенты", Icons.Filled.Person,"clients_route")
    object Calendar: BottomNavItems("Даты",Icons.Filled.DateRange,"calandar_route")
    object Ballance: BottomNavItems("Баланс",Icons.TwoTone.ShoppingCart,"ballance_route")
    object Appatments: BottomNavItems("Объекты",Icons.Filled.Home,"appatments_route")
//    object ClientDitails: BottomNavItems("Клиент",R.drawable.hous/e1,"clietn_ditils")
}