package com.example.navigationexample.presentation.navigation


sealed class Routs() {
    companion object{
        val home: String = "home_screen"
        val addAppatmentScreen: String = "add_appatment_screen"
        val allApartmentsScreen: String = "all_apartments_screen"
        val mainScreenClients: String = "clients_screen"
        val addClientScreen: String = "add_clients_screen"
        val setClientPeriodFromAddClient: String = "set_clients_period_from_add_client"
        val setClientPeriodFromEditClient: String = "set_clients_period_from_edit_client"
        val clientUpdateScreen: String = "client_update_screen"
        val clientDitailsScreen: String = "client_diteils_screen"
        val clientPaymentScreen: String = "client_payment_screen"
        val chartsScreen: String = "charts_screen"
    }
}