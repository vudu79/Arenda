package com.example.navigationexample.presentation.navigation.batton_navigation

import com.example.navigationexample.R


sealed class BottomNavItems(var title:String, var icon:Int, var screen_route:String){

    object Clients : BottomNavItems("Clients", R.drawable.clientsimg,"clients_route")
    object Calendar: BottomNavItems("Calendar",R.drawable.calendar,"calandar_route")
    object Ballance: BottomNavItems("Ballance",R.drawable.many,"ballance_route")
    object Appatments: BottomNavItems("Appatments",R.drawable.house1,"appatments_route")
}