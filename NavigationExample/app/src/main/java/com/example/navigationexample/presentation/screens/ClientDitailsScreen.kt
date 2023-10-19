package com.example.navigationexample.presentation.screens//package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientDitailsScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    clientPhone: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    viewModelClient.getClient(clientPhone)
    val state = viewModelClient.uiClientState
    val inDate = state.value?.inDate ?: 0L
    val outDate = state.value?.outDate ?: 0L
    val ldInDate = LocalDate.ofEpochDay(inDate)
    val ldOutDate = LocalDate.ofEpochDay(outDate)


    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(41, 41, 41))
    ) {

        Text(
            text = makeFullName(state),
            maxLines = 2,
            modifier = Modifier
                .background(Color(41, 41, 41))
                .align(Alignment.CenterHorizontally)
                .padding(15.dp),
            fontSize = 26.sp,
            color = Color.White
        )

        state.value?.let { Color(it.clientColor).copy(alpha = 0.8f) }?.let {
            Divider(
                color = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(3.dp)
            )
        }

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier
                .background(Color(red = 41, green = 41, blue = 41))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

//        Статус _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Статус клиента ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.status ?: "нет данныъх",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 18.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(5.dp))

                }
            }
//        Дата заезда _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Заезд  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = dateToString(state.value?.inDate),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 18.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }

                }
            }
//        Дата выезда _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Выезд  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = dateToString(state.value?.outDate),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 18.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }

                }
            }

//        количество дней _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Количество ночей ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = ChronoUnit.DAYS.between(ldInDate, ldOutDate).toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 18.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }

                }
            }



            item {
                Spacer(modifier = Modifier.padding(10.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
//                            viewModelClient.getAppatmentClients(appatmentName)
                                mainNavController.navigate(route = "${Routs.mainScreenClients}/${currentAppatment?.name}")
                            })
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Назад",
                                modifier = Modifier.size(55.dp),
                                tint = Color(223, 75, 0)
                            )
                        }

                    }
                }
            }

        }
    }
}


fun makeFullName(state: LiveData<Client>): String {
    val fName = state.value?.firstName ?: ""
    val sName = state.value?.secondName ?: ""
    val lName = state.value?.lastName ?: ""
    return "$fName $sName $lName"
}


fun dateToString(longDate: Long?): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return longDate?.let { LocalDate.ofEpochDay(it).format(formatter) }.toString()
}


@Preview
@Composable
private fun ClientDitailsScreen() {
    ClientDitailsScreen()
}
