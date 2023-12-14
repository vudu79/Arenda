package com.example.navigationexample.domain.constants

import androidx.compose.ui.graphics.Color

sealed class Constans {
    object ClientColorsList{
        val clientColorsList = listOf(
            Color(0xFFEF9A9A),
            Color(0xFFF48FB1),
            Color(0xFF80CBC4),
            Color(0xFFA5D6A7),
            Color(0xFFFFCC80),
            Color(0xFFFFAB91),
            Color(0xFF81D4FA),
            Color(0xFFCE93D8),
            Color(0xFFB39DDB)
        )
    }
}


enum class ScoreType(){
    INCOME, EXPENSES
}

enum class SourceEvent {
    CLIENTUPDATE, PAYMENTUPDATE
}

