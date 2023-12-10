package com.example.navigationexample.presentation.events

sealed class BalanceScreenEvents {
    data class onApartmentCheckboxSelected(var apartmentList: List<String>): BalanceScreenEvents()
}