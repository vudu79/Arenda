package com.example.navigationexample.presentation.navigation


sealed class Routs() {
    companion object{
        val home: String = "home_screen"
        val addAppatmentScreen: String = "add_appatment_screen"
        val mainScreenClients: String = "clients_screen"
        val addClientScreen: String = "add_clients_screen"
        val setClientPeriod: String = "set_clients_period"
        val clientDitailsScreen: String = "client_ditails_screen"
    }
}