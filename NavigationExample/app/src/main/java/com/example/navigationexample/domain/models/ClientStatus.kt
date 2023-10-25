package com.example.navigationexample.domain.models

sealed class ClientStatus {
    companion object {
        val statusList:List<String> = listOf("Бронь", "Заселен", "Ожидает",  "Реализован", "Отменен")
        val active: String = "Заселен"
        val booking: String = "Забронировал"
        val intrasting: String = "Заинтересован"
        val done: String = "Реализован"
        val cancel: String = "Отменен"
    }
}