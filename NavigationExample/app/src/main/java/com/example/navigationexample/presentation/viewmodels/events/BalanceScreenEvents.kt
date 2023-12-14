package com.example.navigationexample.presentation.viewmodels.events

sealed class BalanceScreenEvents {
    data class onApartmentCheckboxSelected(var apartmentList: List<String>): BalanceScreenEvents()
}