package com.example.navigationexample.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.domain.models.ClientMonk
import com.example.navigationexample.presentation.screens.common.SimpleCalendarTitle
import com.example.navigationexample.presentation.screens.common.StatusBarColorUpdateEffect
import com.example.navigationexample.presentation.screens.common.rememberFirstCompletelyVisibleMonth
import com.kizitonwose.calendar.compose.HorizontalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import com.kizitonwose.calendar.sample.shared.Flight.Airport
import com.kizitonwose.calendar.sample.shared.displayText
import com.kizitonwose.calendar.sample.shared.generateFlights
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.util.*

private val flights = generateFlights().groupBy { it.time.toLocalDate() }


private val pageBackgroundColor: Color @Composable get() = colorResource(R.color.example_5_page_bg_color)
private val itemBackgroundColor: Color @Composable get() = colorResource(R.color.example_5_item_view_bg_color)
private val toolbarColor: Color @Composable get() = colorResource(R.color.example_5_toolbar_color)
private val selectedItemColor: Color @Composable get() = colorResource(R.color.example_5_text_grey)
private val inActiveTextColor: Color @Composable get() = colorResource(R.color.example_5_text_grey_light)

@Composable
fun CalendarScreen(navController: NavHostController, viewModel: AppatmentViewModel, appatmentName: String) {
//    viewModel.updateDaysMapForCalendar(appatmentName)
//    val dateClientMap = remember { viewModel.dateClientMapForObserve.value }

    val localDateClientMap: MutableMap<LocalDate, MutableSet<ClientMonk>> by viewModel.localDayClientMocKMap.collectAsState(
        initial = mutableMapOf()
    )

    val isLoading: Boolean by viewModel.isLoadingForCalendarScreen

    val currentMonth = remember { YearMonth.now() }
    val startMonth = remember { currentMonth.minusMonths(500) }
    val endMonth = remember { currentMonth.plusMonths(500) }
    var selection by remember { mutableStateOf<CalendarDay?>(null) }
    val daysOfWeek = remember { daysOfWeek() }
    val clientsInSelectedDate = remember {
        derivedStateOf {
            val date = selection?.date
            if (date == null) emptyList() else localDateClientMap[date].orEmpty().toList()
        }
    }



    StatusBarColorUpdateEffect(toolbarColor)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(pageBackgroundColor),
    ) {
        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = daysOfWeek.first(),
            outDateStyle = OutDateStyle.EndOfGrid,
        )
        val coroutineScope = rememberCoroutineScope()
        val visibleMonth = rememberFirstCompletelyVisibleMonth(state)
        LaunchedEffect(visibleMonth) {
            // Clear selection if we scroll to a new month.
            selection = null
        }

        // Draw light content on dark background.
        CompositionLocalProvider(LocalContentColor provides darkColors().onSurface) {
            SimpleCalendarTitle(
                modifier = Modifier
                    .background(toolbarColor)
                    .padding(horizontal = 8.dp, vertical = 12.dp),
                currentMonth = visibleMonth.yearMonth,
                goToPrevious = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.previousMonth)
                    }
                },
                goToNext = {
                    coroutineScope.launch {
                        state.animateScrollToMonth(state.firstVisibleMonth.yearMonth.nextMonth)
                    }
                },
            )

            if (isLoading) {
                CircularProgressIndicator()
            } else {
                HorizontalCalendar(
                    modifier = Modifier.wrapContentWidth(),
                    state = state,
                    dayContent = { day ->
//                        localDateClientMap?.get(day.date)?.let { Log.d("myTag", it.toString()) }
                        CompositionLocalProvider(LocalRippleTheme provides Example3RippleTheme) {
                            val clientMonkList = if (day.position == DayPosition.MonthDate) {
//                            flights[day.date].orEmpty().map { colorResource(it.color) }
                                localDateClientMap[day.date].orEmpty().map { it }

                            } else {
                                emptyList()
                            }
                            Day(
                                day = day,
                                isSelected = selection == day,
                                clientMonks = clientMonkList,
                            ) { clicked ->
                                selection = clicked
                            }
                        }
                    },
                    monthHeader = {
                        MonthHeader(
                            modifier = Modifier.padding(vertical = 8.dp),
                            daysOfWeek = daysOfWeek,
                        )
                    },
                )

            }

            Divider(color = pageBackgroundColor)
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(items = clientsInSelectedDate.value) { client ->
//                    ClientInformation(client)
                    ClientItemRow(client.client, navController, viewModel)
                }
            }
        }
    }
}

@Composable
private fun Day(
    day: CalendarDay,
    isSelected: Boolean = false,
    clientMonks: List<ClientMonk?> = emptyList(),
    onClick: (CalendarDay) -> Unit = {},
) {
    Box(
        modifier = Modifier
            .aspectRatio(1f) // This is important for square-sizing!
            .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) selectedItemColor else Color.Transparent,
            )
            .padding(1.dp)
            .background(color = itemBackgroundColor)
            // Disable clicks on inDates/outDates
            .clickable(
                enabled = day.position == DayPosition.MonthDate,
                onClick = { onClick(day) },
            ),
    ) {
        val textColor = when (day.position) {
            DayPosition.MonthDate -> Color.Unspecified
            DayPosition.InDate, DayPosition.OutDate -> inActiveTextColor
        }
        Text(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 3.dp, end = 4.dp),
            text = day.date.dayOfMonth.toString(),
            color = textColor,
            fontSize = 12.sp,
        )


        if (clientMonks.size == 2) {
            val left = clientMonks.first { it?.ieEndDay == true }
            val right = clientMonks.first { it?.ieStartDay == true }
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
            ) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .height(8.dp)
                        .background(Color(left?.color ?: 1)),
                )

                Box(
                    modifier = with(Modifier) {
                        fillMaxWidth()
                            .height(8.dp)
                            .background(Color(right?.color ?: 1))
                    },
                )
            }
        } else if (clientMonks.size == 1) {
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(8.dp)
                        .background(Color(clientMonks[0]?.color ?: 1)),
                )
            }
        }

    }
}


//
//
//            for (clientMock in clientMonks) {
//                val color = clientMock?.color ?: 1
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(5.dp)
//                        .background(Color(color)),
//                )
//            }


@Composable
private fun MonthHeader(
    modifier: Modifier = Modifier,
    daysOfWeek: List<DayOfWeek> = emptyList(),
) {
    Row(modifier.fillMaxWidth()) {
        for (dayOfWeek in daysOfWeek) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                color = Color.White,
                text = dayOfWeek.displayText(uppercase = true),
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable
private fun LazyItemScope.ClientInformation(clientMonk: ClientMonk) {
    Row(
        modifier = Modifier
            .fillParentMaxWidth()
//            .height(IntrinsicSize.Max)
            .fillMaxHeight(0.8f),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        Box(
            modifier = Modifier
                .border(width = 3.dp, color = Color(clientMonk.color), RoundedCornerShape(8.dp) )
                .fillParentMaxWidth(1 / 5f)
                .aspectRatio(1f),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = clientMonk.client.name,
                textAlign = TextAlign.Center,
                lineHeight = 17.sp,
                fontSize = 12.sp,
            )
        }
        Box(
            modifier = Modifier
                .background(color = itemBackgroundColor)
                .weight(1f)
                .fillMaxHeight(),
        ) {
//            AirportInformation(flight.departure, isDeparture = true)
        }
//        Box(
//            modifier = Modifier
//                .background(color = itemBackgroundColor)
//                .weight(1f)
//                .fillMaxHeight(),
//        ) {
//            AirportInformation(flight.destination, isDeparture = false)
//        }
    }
    Divider(color = pageBackgroundColor, thickness = 2.dp)
}

@Composable
private fun AirportInformation(airport: Airport, isDeparture: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
    ) {
        val resource = if (isDeparture) {
            R.drawable.ic_airplane_takeoff
        } else {
            R.drawable.ic_airplane_landing
        }
        Box(
            modifier = Modifier
                .weight(0.3f)
                .fillMaxHeight()
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd,
        ) {
            Image(painter = painterResource(resource), contentDescription = null)
        }
        Column(
            modifier = Modifier
                .weight(0.7f)
                .fillMaxHeight()
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = airport.code,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Black,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = airport.city,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

// The default dark them ripple is too bright so we tone it down.
private object Example3RippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(Color.Gray, lightTheme = false)

    @Composable
    override fun rippleAlpha() = RippleTheme.defaultRippleAlpha(Color.Gray, lightTheme = false)
}

