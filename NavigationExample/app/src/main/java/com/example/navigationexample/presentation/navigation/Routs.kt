package com.example.navigationexample.presentation.navigation


sealed class Routs() {
    companion object{
        val home: String = "home_screen"
        val addAppatmentScreen: String = "add_appatment_screen"
        val mainScreenClients: String = "clients_screen"
        val addClientScreen: String = "add_clients_screen"
        val setClientPeriodFromAddClient: String = "set_clients_period_from_add_client"
        val setClientPeriodFromEditClient: String = "set_clients_period_from_edit_client"
        val clientDitailsScreen: String = "client_ditails_screen"
    }
}