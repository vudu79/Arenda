package com.example.navigationexample.presentation.screens

import android.icu.lang.UCharacter.DecompositionType.NARROW
import android.icu.util.Calendar.SUNDAY
import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.boguszpawlowski.composecalendar.SelectableCalendar
import io.github.boguszpawlowski.composecalendar.day.NonSelectableDayState
import io.github.boguszpawlowski.composecalendar.header.MonthState
import io.github.boguszpawlowski.composecalendar.rememberSelectableCalendarState
import io.github.boguszpawlowski.composecalendar.selection.SelectionMode
import java.time.DayOfWeek
import java.time.LocalDate
import java.util.*



@Composable
fun CalendarScreen() {
    MainScreen()

}
@Composable
fun MainScreen() {
    val state = rememberSelectableCalendarState(
        confirmSelectionChange = { onSelectionChanged(it); true },
        initialSelectionMode = SelectionMode.Multiple,
    )
    SelectableCalendar(
        calendarState = state,
        modifier = Modifier.animateContentSize(),

    )
}

fun onSelectionChanged(selection: List<LocalDate>) {
    Log.d("myTag", selection.toString())
}


@Composable
private fun DayContent(dayState: NonSelectableDayState) {
    Text(
        text = dayState.date.dayOfMonth.toString(),
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.h6,
    )
}


@Composable
private fun MonthHeader(monthState: MonthState) {
    Row {
        Text(monthState.currentMonth.year.toString(), style = MaterialTheme.typography.h3)
        Text(monthState.currentMonth.month.name, style = MaterialTheme.typography.h3)
        IconButton(onClick = { monthState.currentMonth = monthState.currentMonth.plusMonths(1) }) {
            Image(
                imageVector = Icons.Default.Star,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                contentDescription = "Next",
            )
        }
    }
}

@Composable
private fun MonthContainer(content: @Composable (PaddingValues) -> Unit) {
    Card(
        elevation = 0.dp,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, Color.LightGray),
        content = { content(PaddingValues(4.dp)) },
    )
}

