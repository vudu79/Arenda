package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*
import com.example.navigationexample.presentation.screens.common.ContinuousSelectionHelper.getSelection
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.CalendarMonth
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.daysOfWeek
import com.kizitonwose.calendar.sample.compose.Example2Page
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth

//private val primaryColor = Color.White.copy(alpha = 0.2f)
private val primaryColor = Color(223, 75, 0)
private val selectionColor = primaryColor

//private val continuousSelectionColor = Color.Red.copy(alpha = 0.2f)
private val continuousSelectionColor = Color(223, 75, 0).copy(alpha = 0.3f)
private val orangeColor = Color(223, 75, 0)


private val pageBackgroundColor: Color @Composable get() = colorResource(R.color.example_5_page_bg_color)
private val itemBackgroundColor: Color @Composable get() = colorResource(R.color.example_5_item_view_bg_color)
private val toolbarColor: Color @Composable get() = colorResource(R.color.example_5_toolbar_color)
private val selectedItemColor: Color @Composable get() = colorResource(R.color.example_5_text_grey)
private val inActiveTextColor: Color @Composable get() = colorResource(R.color.example_5_text_grey_light)


@Composable
fun SetDatePeriodScreen(
    navController: NavHostController,
    viewModel: AppatmentViewModel,
    appatmentName: String,
    close: () -> Unit = { navController.navigate("${Routs.addClientScreen}?appatment_name=$appatmentName") },
    dateSelected: (startDate: LocalDate, endDate: LocalDate) -> Unit = { _, _ -> },

    ) {

    val planedApartmentDays: List<LocalDate> by viewModel.allApartmentPlanedDays.collectAsState(
        initial = listOf()
    )

    val isLoading: Boolean by viewModel.isLoadingForSetPeriodScreen

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth }
    val endMonth = remember { currentMonth.plusMonths(12) }
    val today = remember { LocalDate.now() }
    var selection by remember { mutableStateOf(DateSelection()) }
    val daysOfWeek = remember { daysOfWeek() }


    StatusBarColorUpdateEffect(toolbarColor)
    MaterialTheme(colors = MaterialTheme.colors.copy(primary = primaryColor)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(pageBackgroundColor),
        ) {
            Column {
                val state = rememberCalendarState(
                    startMonth = startMonth,
                    endMonth = endMonth,
                    firstVisibleMonth = currentMonth,
                    firstDayOfWeek = daysOfWeek.first(),
                )

                CalendarTop(
                    daysOfWeek = daysOfWeek,
                    selection = selection,
                    close = close,
                    clearDates = { selection = DateSelection() },
                    save = {
                        val (startDate, endDate) = selection
                        if (startDate != null && endDate != null) {
                            dateSelected(startDate, endDate)
                            viewModel.dateInString1.value = startDate.toString()
                            viewModel.dateOutString1.value = endDate.toString()
                            viewModel.dateInLong1.value = startDate.toEpochDay()
                            viewModel.dateOutLong1.value = endDate.toEpochDay()

                            navController.navigate(Routs.addClientScreen)
                        }
                    },
                )

                if (isLoading) {
                    CircularProgressIndicator()
                } else {
                    VerticalCalendar(
                        state = state,
                        contentPadding = PaddingValues(bottom = 100.dp),
                        dayContent = { value ->
                            Day(
                                value,
                                today = today,
                                selection = selection,
                                planedDays = planedApartmentDays ?: listOf()

                            ) { day ->
                                if (day.position == DayPosition.MonthDate &&
                                    (day.date == today || day.date.isAfter(today))
                                ) {
                                    selection = getSelection(
                                        clickedDate = day.date,
                                        dateSelection = selection,
                                    )
                                }
                            }
                        },
                        monthHeader = { month -> MonthHeader(month) },
                    )
                }

            }

        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    today: LocalDate,
    planedDays: List<LocalDate>,
    selection: DateSelection,
    onClick: (CalendarDay) -> Unit,
) {
    var textColor = Color.Transparent
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .clickable(
                enabled = day.position == DayPosition.MonthDate
                        && day.date >= today
                        && (!planedDays.contains(day.date)),
                showRipple = false,
                onClick = { onClick(day) },
            )
            .backgroundHighlight(
                day = day,
                today = today,
                selection = selection,
                selectionColor = selectionColor,
                continuousSelectionColor = continuousSelectionColor,
                planedDays = planedDays
            ) { textColor = it },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun MonthHeader(calendarMonth: CalendarMonth) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 8.dp, start = 16.dp, end = 16.dp),
    ) {
        Text(
            textAlign = TextAlign.Center,
            text = calendarMonth.yearMonth.displayText(),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun CalendarTop(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek>,
    selection: DateSelection,
    close: () -> Unit,
    clearDates: () -> Unit,
    save: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                Icon(
//                    modifier = Modifier
//                        .fillMaxHeight()
//                        .aspectRatio(1f)
//                        .clip(CircleShape)
//                        .clickable(onClick = close)
//                        .padding(12.dp),
//                    painter = painterResource(id = R.drawable.ic_close),
//                    contentDescription = "Close",
//                )


                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp),
                    onClick = close,
                ) {
                    Text(text = "Close")
                }

                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp),
                    onClick = save,
                    enabled = selection.daysBetween != null,
                ) {
                    Text(text = "Save")
                }

                Button(
                    modifier = Modifier
                        .height(40.dp)
                        .width(100.dp),
                    onClick = clearDates,
                ) {
                    Text(text = "Clear")
                }


//                Text(
//                    modifier = Modifier
//                        .clip(RoundedCornerShape(percent = 50))
//                        .clickable(onClick = clearDates)
//                        .padding(horizontal = 16.dp, vertical = 8.dp),
//                    text = "Clear",
//                    fontWeight = FontWeight.Medium,
//                    textAlign = TextAlign.End,
//                    color = Color.White
//                )
            }
            val daysBetween = selection.daysBetween
            val text = if (daysBetween == null) {
                "Select dates"
            } else {
                // Ideally you'd do this using the strings.xml file
//                "$daysBetween ${if (daysBetween == 1L) "night" else "nights"} in Munich"
                "c ${selection.startDate} по ${selection.endDate}"
            }
            Text(
                modifier = Modifier.padding(horizontal = 14.dp),
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 4.dp),
            ) {
                for (dayOfWeek in daysOfWeek) {
                    Text(
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = Color.DarkGray,
                        text = dayOfWeek.displayText(),
                        fontSize = 15.sp,
                    )
                }
            }
        }
        Divider()
    }
}

@Composable
private fun CalendarBottom(
    modifier: Modifier = Modifier,
    selection: DateSelection,
    save: () -> Unit,
) {
    Column(modifier.fillMaxWidth()) {
        Divider()
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {

            Spacer(modifier = Modifier.weight(1f))
            Button(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp),
                onClick = save,
                enabled = selection.daysBetween != null,
            ) {
                Text(text = "Save")
            }
        }
    }
}

@Preview(heightDp = 800)
@Composable
private fun Example2Preview() {
    Example2Page()
}
