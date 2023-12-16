package com.example.navigationexample.presentation.navigation.batton_navigation

import com.example.navigationexample.R


sealed class BottomNavItems(var title:String, var icon:Int, var screenRoute:String){

    object Clients : BottomNavItems("Клиенты", R.drawable.client1_round,"clients_route")
    object Calendar: BottomNavItems("Даты",R.drawable.calendar,"calandar_route")
    object Ballance: BottomNavItems("Баланс",R.drawable.many,"ballance_route")
    object Appatments: BottomNavItems("Объекты",R.drawable.house1,"appatments_route")
    object ClientDitails: BottomNavItems("Клиент",R.drawable.house1,"clietn_ditils")
}