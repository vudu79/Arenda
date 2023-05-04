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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
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

    val currentAppatment by viewModel.currentApartment.observeAsState()
    val focusManager = LocalFocusManager.current
    val state = viewModel.validateFormState
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.Success -> {
                    Toast.makeText(
                        context,
                        "Registration successful",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


//    val firstName = remember { mutableStateOf(viewModel.clientFirstName.value) }
//    val secondName = remember { mutableStateOf(viewModel.clientSecondName.value) }
//    val lastName = remember { mutableStateOf(viewModel.clientLastName.value) }
//    var phoneNumber by rememberSaveable { mutableStateOf(viewModel.phone.value) }
//    var documentNunber by rememberSaveable { mutableStateOf(viewModel.documentNumber.value) }
//    var documentDitails by rememberSaveable { mutableStateOf(viewModel.documentDitails.value) }
//    val members = remember { mutableStateOf(viewModel.members.value) }
//    val payment = remember { mutableStateOf(viewModel.payment.value) }
//    val prepayment = remember { mutableStateOf(viewModel.prepayment.value) }
//    val colorClient = remember { mutableStateOf(viewModel.colorClient.value) }
//    val sity = remember { mutableStateOf(viewModel.sity.value) }


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
                    text = "Регистрация клиента",
                    modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(223, 75, 0).copy(alpha = 0.9f)
                )
                Text(
                    text = "объект: \"${viewModel.currentApartment.value?.name ?: ""}\" | срок: " +
                            (viewModel.currentApartment.value?.rentalPeriod ?: ""),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
                    color = Color(223, 75, 0).copy(alpha = 0.9f)

                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxWidth()
                ) {

                    OutlinedTextField(
                        value = state.firstName,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.FirstNameChanged(it))
                        },
                        placeholder = { Text(text = "Имя", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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

                    state.secondName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.SecondNameChanged(it))
                            },

                            placeholder = { Text(text = "Отчество", color = Black) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                    }
                    state.lastName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.LastNameChanged(it))
                            },
                            placeholder = { Text(text = "Фамилия", color = Black) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                    }

                    PhoneField(
                        state.phone,
                        mask = "+7(000)-000-00-00",
                        maskNumber = '0',
                        onPhoneChanged = {
                            viewModel.onFormEvent(ValidationFormEvent.PhoneChanged(it))
                        })



                    state.documentNamber?.let {
                        PhoneField(
                            it,
                            mask = "0000-000000",
                            maskNumber = '0',
                            onPhoneChanged = { phone ->
                                viewModel.onFormEvent(
                                    ValidationFormEvent.DocumentNamberChanged(
                                        phone
                                    )
                                )
                            })
                    }


                    state.documentDitails?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.DocumentDitailsChanged(it))
                            },
                            placeholder = {
                                Text(
                                    text = "Паспорт - когда, кем выдан",
                                    color = Black
                                )
                            },
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
                                imeAction = ImeAction.Next, keyboardType = KeyboardType.Phone
                            ),
                            keyboardActions = KeyboardActions(onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            }),
                            maxLines = 4
                        )
                    }

                    ColourButton(colors, onColorSelected = {
                        viewModel.colorClient.value = it.toArgb()
//                        Log.d("myTag", it.toArgb().toString())
                    })

                    state.members?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.MembersChanged(it))
                            },
                            placeholder = { Text(text = "Количество человек", color = Black) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                    }


                    OutlinedTextField(
                        value = "c ${state.dateInString} по ${state.dateOutString} ",
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.InLongDateChanged(viewModel.dateInLong1.value!!))
                            viewModel.onFormEvent(ValidationFormEvent.InStringDateChanged(viewModel.dateInString1.value!!))
                            viewModel.onFormEvent(ValidationFormEvent.OutLongDateChanged(viewModel.dateOutLong1.value!!))
                            viewModel.onFormEvent(ValidationFormEvent.OutStringDateChanged(viewModel.dateOutString1.value!!))
                        },

                        placeholder = { Text(text = "Период проживания", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
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


                    OutlinedTextField(
                        value = state.prepayment,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.PrepaymentChanged(it))
                        },

                        placeholder = { Text(text = "Внесенный залог", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                        value = state.payment,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.PaymentChanged(it))
                        },

                        placeholder = { Text(text = "Стоимость суток", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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

                    state.sity?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.SityChanged(it))
                            },

                            placeholder = { Text(text = "Пункт отправления", color = Black) },
                            singleLine = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                    }


                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(modifier = Modifier.padding(end = 80.dp),
                            onClick = {
                                viewModel.getAppatmentClients(appatmentName)
                                navController.navigate(Routs.home)
                            })
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Назад",

                                modifier = Modifier.size(55.dp),
                                tint = Color(223, 75, 0)
                            )
                        }

                        IconButton(
                            onClick = {
                                viewModel.onFormEvent(ValidationFormEvent.onSubmit)


//                            if (viewModel.clientFirstName.value.isNullOrEmpty()) {
//                                Toast.makeText(
//                                    context, "Имя клиента - обязательное поле!", Toast.LENGTH_SHORT
//                                ).show()
//                            } else if (viewModel.phone.value.isNullOrEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Контатный телефон клиента - обязательное поле!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                            } else if (viewModel.dateInString1.value.isNullOrEmpty() && viewModel.dateOutString1.value.isNullOrEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Период проживания  - обязательное поле!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else if (viewModel.prepayment.value.isNullOrEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Внесеный залог - обязательное поле!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else if (viewModel.payment.value.isNullOrEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Стwоимость суток - обязательное поле!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//
//                            } else {
//                                viewModel.addClient(
//                                    Client(
//                                        firstName = viewModel.clientFirstName.value!!,
//                                        secondName = viewModel.clientSecondName.value,
//                                        lastName = viewModel.clientLastName.value,
//                                        phone = "+7${phoneNumber}",
//                                        documentNunber = "${documentNunber}",
//                                        documentDitails = "${documentDitails}",
//                                        inDate = viewModel.dateInLong1.value!!,
//                                        outDate = viewModel.dateOutLong1.value!!,
//                                        members = viewModel.members.value?.trim()?.toInt(),
//                                        prepayment = viewModel.prepayment.value!!.trim().toInt(),
//                                        payment = viewModel.payment.value!!.trim().toInt(),
//                                        clientColor = viewModel.colorClient.value!!,
//                                        sity = viewModel.sity.value,
//                                        appatment_name = currentAppatment?.name ?: "222"
//                                    )
//                                )
//                                Toast.makeText(
//                                    context, "Новый клиент зарегестрирован!", Toast.LENGTH_SHORT
//                                ).show()
//                                viewModel.getAppatmentClients(currentAppatment?.name ?: "")
//                                currentAppatment?.name?.let { viewModel.updateDaysMapForCalendar(it) }
//                                currentAppatment?.name?.let { viewModel.updateApartmentPlanedDays(it) }
//                                navController.navigate(route = "${Routs.mainScreenClients}?appatment_name=$appatmentName")
//                            }
                            }
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_check_24),
                                contentDescription = "Добавить объект недвижимости",

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

