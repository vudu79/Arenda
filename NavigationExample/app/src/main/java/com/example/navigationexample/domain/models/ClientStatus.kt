package com.example.navigationexample.domain.models

sealed class ClientStatus {
    companion object{
        val active: String = "active"
        val waiting: String = "waiting"
        val leaving: String = "leaving"
        val potential: String = "potential"
        val canseled: String = "canseled"
    }
}