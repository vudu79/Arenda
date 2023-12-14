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
    viewModelAppatment: ApartmentViewModel,
    clientPhone: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    viewModelClient.getClientState(clientPhone)
    val state = viewModelClient.uiClientState

    val inDate = state.value?.inDate ?: 0L
    val outDate = state.value?.outDate ?: 0L
    val ldInDate = LocalDate.ofEpochDay(inDate)
    val ldOutDate = LocalDate.ofEpochDay(outDate)
    val totalDays = ChronoUnit.DAYS.between(ldInDate, ldOutDate).toInt()
    val payment = state.value?.pricePerDay ?: 0
    val overPayment = state.value?.overPayment ?: 0
    val overMembers = state.value?.overMembers ?: 0
    val members = state.value?.members ?: 0
    val prePaymentPercent = state.value?.prePaymentPercent ?: 0

    val totalCoast = if (members <= overMembers) {
        (payment * totalDays)
    } else {
        (payment * totalDays) + (members - overMembers) * totalDays * overPayment
    }
    val prepayment = (totalCoast / 100) * prePaymentPercent

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
                color = it, modifier = Modifier
                    .fillMaxWidth()
                    .width(5.dp)
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
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
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
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.status ?: "нет данных",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                }
            }

//        Доход от клиениа
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {

                        Text(
                            text = "Оплачено (предоплата/бронь): ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.completedPrePayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = if (viewModelClient.isPrePaymentComplete.value == true) {
                                Color(5, 224, 14, 255)
                            } else {
                                Color(247, 21, 4, 255)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {

                        Text(
                            text = "Оплачено (стоимость проживания): ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.completedPayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = if (viewModelClient.isPaymentComplete.value == true) {
                                Color(5, 224, 14, 255)
                            } else {
                                Color(247, 21, 4, 255)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {

                        Text(
                            text = "Оплачено (залог): ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.completedPledge.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = if (viewModelClient.isPledgeComplete.value == true) {
                                Color(5, 224, 14, 255)
                            } else {
                                Color(247, 21, 4, 255)
                            }                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

            //    стоимость бронирования
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Размер предоплаты (${state.value?.prePaymentPercent.toString()}%)  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.prePayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )

                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    полная стоимость брони
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Полная стоимость проживания ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = totalCoast.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    полная стоимость брони с учетом залога
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Стоимость с учетом предоплаты ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = (totalCoast - prepayment).toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    размер залога на имущество
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Размер залога (обеспечительный платеж) ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.pledge.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//        Дата заезда _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
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
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = dateToString(state.value?.inDate),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }
//        Дата выезда _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
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
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = dateToString(state.value?.outDate),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//        количество дней _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
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
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )


                        Text(
                            text = totalDays.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }


//    количество гостей
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Количество гостей: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.members.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    человек в тарифе
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Человек в тарифе за сутки: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.overMembers.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    стоимость тарифа
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Стоимость тарифа ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.pricePerDay.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//    стоимость доп места
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Стоимость одного доп. места ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.overPayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }


//   телефон клиента
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Телефон клиента: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = "+7${state.value?.phone}",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

            //   паспортные данные клиента
            item {

                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Серия и номер паспорта: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.documentNumber.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Кем и когда выдан паспорт: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.documentDitails.toString(),
                            maxLines = 5,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//   данные для трансфера
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Информация по трансферу: ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.transferInfo.toString(),
                            maxLines = 5,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                }
            }

//   рекламные ресурс
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Откуда клиент узнал о вас: ",
                            maxLines = 2,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 16.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.value?.referer.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 16.sp,
                            color = Color(254, 253, 253, 255)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
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
                        IconButton(onClick = {
//                            viewModelClient.getAppatmentClients(appatmentName)
                            mainNavController.navigate(route = "${Routs.apartmentFinanceScreen}/${currentAppatment?.name}")
                        }) {
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