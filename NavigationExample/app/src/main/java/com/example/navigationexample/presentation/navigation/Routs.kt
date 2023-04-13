package com.example.navigationexample.presentation.navigation


sealed class Routs() {
    companion object{
        val home: String = "home_screen"
        val addAppatmentScreen: String = "add_appatment_screen"
        val mainScreenClients: String = "clients_screen"
    }
}