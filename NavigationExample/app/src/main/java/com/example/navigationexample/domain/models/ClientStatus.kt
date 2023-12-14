package com.example.navigationexample.domain.models

sealed class ClientStatus {
    companion object {
        val statusList:List<String> = listOf("Заинтересован", "Забронировал", "Заселен",  "Реализован", "Отменен")
        val intrasting: String = "Заинтересован"
        val booking: String = "Забронировал"
        val active: String = "Заселен"
        val done: String = "Реализован"
        val cancel: String = "Отменен"
    }
}