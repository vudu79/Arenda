package com.example.navigationexample.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(
    navController: NavHostController,
    viewModel: AppatmentViewModel,
    appatmentName: String,

    ) {

    val colors = listOf(
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


    var phoneNumber by rememberSaveable { mutableStateOf(viewModel.phone.value) }

    val currentAppatment by viewModel.currentApartment.observeAsState()
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    val name = remember { mutableStateOf(viewModel.clientName.value) }
    val phone = remember { mutableStateOf(viewModel.phone.value) }
    val members = remember { mutableStateOf(viewModel.members.value) }
    val payment = remember { mutableStateOf(viewModel.payment.value) }
    val prepayment = remember { mutableStateOf(viewModel.prepayment.value) }
    val colorClient = remember { mutableStateOf(viewModel.colorClient.value) }
    val sity = remember { mutableStateOf(viewModel.sity.value) }
    val appatmentNameState = remember { mutableStateOf(currentAppatment) }


    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(41, 41, 41))
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()

            ) {}

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))
                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Новый клиент для ${viewModel.currentApartment.value}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    color = Color.White


                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = name.value!!,
                        onValueChange = {
                            name.value = it
                            viewModel.clientName.value = it
                        },
                        label = { Text(text = "ФИО", color = Black) },
                        placeholder = { Text(text = "ФИО", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),

                        )


                    phoneNumber?.let {
                        PhoneField(
                            it,
                            mask = "+7(000)-000-00-00",
                            maskNumber = '0',
                            onPhoneChanged = {
                                phoneNumber = it
                                viewModel.phone.value = it
                            })
                    }


//                    OutlinedTextField(
//                        value = phone.value!!,
//                        onValueChange = {
//                            viewModel.phone.value = it
//                            phone.value = it
//                        },
//
//                        label = { Text(text = "Контактный телефон", color = Black) },
//                        placeholder = { Text(text = "Контактный телефон", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black,
//                            textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        ),
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone
//                        ),
//                        keyboardActions = KeyboardActions(onNext = {
//                            focusManager.moveFocus(FocusDirection.Down)
//                        }),
//                    )


                    ColourButton(colors, onColorSelected = {
                        viewModel.colorClient.value = it.toArgb()
//                        Log.d("myTag", it.toArgb().toString())
                    })


                    OutlinedTextField(
                        value = members.value!!,
                        onValueChange = {
                            viewModel.members.value = it
                            members.value = it
                        },

                        label = { Text(text = "Количество человек", color = Black) },
                        placeholder = { Text(text = "Количество человек", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )


                    OutlinedTextField(
                        value = "c ${viewModel.dateInString1.value.toString()} по ${viewModel.dateOutString1.value.toString()} ",
                        onValueChange = {
                            viewModel.dateInString = it
                        },
                        label = { Text(text = "Период проживания", color = Black) },
                        placeholder = { Text(text = "Период проживания", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp)
                            .clickable {
                                navController.navigate(route = "${Routs.setClientPeriod}?appatment_name=$appatmentName")
//                                viewModel.showDatePickerDialog(context, "in")

                            },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138),

                            ),
                        enabled = false,
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )

//                    OutlinedTextField(
//                        value = viewModel.dateOutString,
//                        onValueChange = {
//                            viewModel.dateOutString = it
//                        },
//                        label = { Text(text = "Дата убытия", color = Black) },
//                        placeholder = { Text(text = "Дата убытия", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(5.dp)
//                            .clickable {
//                                viewModel.showDatePickerDialog(context, "out")
//
//                            },
//                        keyboardOptions = KeyboardOptions(
//                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
//                        ),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black,
//                            textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        ),
//                        enabled = false,
//                        keyboardActions = KeyboardActions(onNext = {
//                            focusManager.moveFocus(FocusDirection.Down)
//                        }),
//                    )

                    OutlinedTextField(
                        value = prepayment.value!!,
                        onValueChange = {
                            viewModel.prepayment.value = it
                            prepayment.value = it
                        },
                        label = { Text(text = "Внесенный залог", color = Black) },
                        placeholder = { Text(text = "Внесенный залог", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),

                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )

                    OutlinedTextField(
                        value = payment.value!!,
                        onValueChange = {
                            viewModel.payment.value = it
                            payment.value = it
                        },
                        label = { Text(text = "Стоимость суток", color = Black) },
                        placeholder = { Text(text = "Стоимость суток", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )

                    OutlinedTextField(
                        value = sity.value!!,
                        onValueChange = {
                            viewModel.sity.value = it
                            sity.value = it
                        },
                        label = { Text(text = "Пункт отправления", color = Black) },
                        placeholder = { Text(text = "Пункт отправления", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Text
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),

                        )


                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(223, 75, 0)),
                        onClick = {
                            if (viewModel.clientName.value.isNullOrEmpty()) {
                                Toast.makeText(
                                    context, "ФИО клиента - обязательное поле!", Toast.LENGTH_SHORT
                                ).show()
                            } else if (viewModel.phone.value.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Контатный телефон клиента - обязательное поле!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else if (viewModel.dateInString1.value.isNullOrEmpty() && viewModel.dateOutString1.value.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Период проживания  - обязательное поле!",
                                    Toast.LENGTH_SHORT
                                ).show()
//                            } else if (viewModel.dateOutLong == 0L) {
//                                Toast.makeText(
//                                    context, "Дата убытия  - обязательное поле!", Toast.LENGTH_SHORT
//                                ).show()
                            } else if (viewModel.prepayment.value.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Внесеный залог - обязательное поле!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (viewModel.payment.value.isNullOrEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Стwоимость суток - обязательное поле!",
                                    Toast.LENGTH_SHORT
                                ).show()

                            } else {
                                viewModel.addClient(
                                    Client(
                                        name = viewModel.clientName.value!!,
                                        phone = phoneNumber.toString(),
//                                        phone = viewModel.phone.value!!,
                                        inDate = viewModel.dateInLong1.value!!,
                                        outDate = viewModel.dateOutLong1.value!!,
                                        members = viewModel.members.value!!.trim().toInt(),
                                        prepayment = viewModel.prepayment.value!!.trim().toInt(),
                                        payment = viewModel.payment.value!!.trim().toInt(),
                                        clientColor = viewModel.colorClient.value!!,
                                        sity = viewModel.sity.value,
                                        appatment_name = currentAppatment?.name ?: "222"
                                    )
                                )
                                Toast.makeText(
                                    context, "Новый клиент зарегестрирован!", Toast.LENGTH_SHORT
                                ).show()
                                viewModel.getAppatmentClients(currentAppatment?.name ?: "")
                                currentAppatment?.name?.let { viewModel.updateDaysMapForCalendar(it) }
                                currentAppatment?.name?.let { viewModel.updateApartmentPlanedDays(it) }
                                navController.navigate(route = "${Routs.mainScreenClients}?appatment_name=$appatmentName")
                            }
                        }
                    ) {
                        Text(text = "Добавить", fontSize = 20.sp, color = Black)
                    }

                    Spacer(modifier = Modifier.padding(3.dp))

                    Text(
                        text = "Назад",
                        color = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.getAppatmentClients(appatmentName)
                            navController.navigate(Routs.home)
                        })
                }
            }

        }
    }
}

//    Column(Modifier.padding(16.dp)) {
//        val textState = remember { mutableStateOf(TextFieldValue()) }
//        TextField(
//            value = textState.value,
//            onValueChange = { textState.value = it }
//        )
//        Text("The textfield has this text: " + textState.value.text)
//
//        Button(onClick = onHome) {
//            Text("Назад")
//        }
//
//    }

