package com.example.navigationexample.presentation.screens


import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(
    navController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    appatmentName: String,
) {

//    val currentAppatment by viewModelClient.currentApartment.observeAsState()
    val focusManager = LocalFocusManager.current
    val state = viewModelClient.validateFormState
    val context = LocalContext.current
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    val expanded = remember { mutableStateOf(false) }
    val items = ClientStatus.statusList
    val disabledValue = "B"
    val selectedIndex = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = context) {
        viewModelClient.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.InsertSuccess -> {

                    Toast.makeText(
                        context, "Новый клиент зарегестрирован!", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(route = "${Routs.mainScreenClients}/$appatmentName")
                }
                is ValidatAllFieldsResultEvent.UpdateSuccess -> {

                }
                is ValidatAllFieldsResultEvent.UpdateWrong -> {

                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(41, 41, 41))
    ) {
        Box(
            modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.BottomCenter
        ) {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(41, 41, 41))
            ) {

                item {
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
                            text = "объект: \"${currentAppatment?.name ?: "_"}\"",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 1.dp, bottom = 1.dp),
                            color = Color(223, 75, 0).copy(alpha = 0.9f)

                        )
                    }
                }

                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(0.99f)
                            .padding(top = 20.dp, bottom = 5.dp, start = 5.dp, end = 5.dp)
                            .background(
                                Color(142, 143, 138)
                            ),
//                            .wrapContentSize(Alignment.Center),
                        contentAlignment = Alignment.BottomStart
                    ) {
                        Text(
                            text = "Статус клиента - ${items[selectedIndex.value]}",
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.CenterStart)
                                .padding(start = 10.dp, top = 15.dp, bottom = 15.dp)
                                .clickable(onClick = { expanded.value = true }),
                            fontSize = 16.sp,
                            color = Black,
                        )
                        DropdownMenu(
                            expanded = expanded.value,
                            onDismissRequest = { expanded.value = false },
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color.Transparent
                                )
                        ) {
                            items.forEachIndexed { index, s ->
                                DropdownMenuItem(onClick = {
                                    selectedIndex.value = index
                                    expanded.value = false
                                    viewModelClient.onFormEvent(ValidationFormEvent.StatusChanged(s))
                                }) {
                                    val disabledText = if (s == disabledValue) {
                                        " (Disabled)"
                                    } else {
                                        ""
                                    }
                                    Text(text = s + disabledText)
                                }
                            }
                        }
                    }
                }

//Имя клиента
                item {
                    OutlinedTextField(
                        value = state.firstName,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.FirstNameChanged(it))
                        },
                        placeholder = { Text(text = "Имя", color = Black) },
                        isError = state.firstNameError != null,
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
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )
                    if (state.firstNameError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.firstNameError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    state.secondName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModelClient.onFormEvent(ValidationFormEvent.SecondNameChanged(it))
                            },

                            placeholder = { Text(text = "Отчество", color = Black) },
                            isError = state.secondNameError != null,
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
                    if (state.secondNameError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.secondNameError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    state.lastName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModelClient.onFormEvent(ValidationFormEvent.LastNameChanged(it))
                            },
                            placeholder = { Text(text = "Фамилия", color = Black) },
                            isError = state.lastNameError != null,
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
                    if (state.lastNameError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.lastNameError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }

                item {
                    PhoneField(
                        state.phone,
                        placeHolder = "Контактный телефон",
                        mask = "000-000-00-00",
                        maskNumber = '0',
                        onPhoneChanged = {
                            viewModelClient.onFormEvent(ValidationFormEvent.PhoneChanged(it))
                        },
                        errorMessage = state.phoneError,
                        modifier = Modifier.align(Alignment.BottomStart)
                    )
                }

                item {
                    state.documentNamber?.let {
                        PhoneField(
                            value = it,
                            placeHolder = "Паспорт: серия и номер",
                            mask = "0000-000000",
                            maskNumber = '0',
                            onPhoneChanged = { phone ->
                                viewModelClient.onFormEvent(
                                    ValidationFormEvent.DocumentNamberChanged(
                                        phone
                                    )
                                )
                            },
                            errorMessage = state.documentNamberError,
                            modifier = Modifier.align(Alignment.BottomStart)
                        )
                    }
                }

                item {
                    state.documentDitails?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModelClient.onFormEvent(ValidationFormEvent.DocumentDitailsChanged(it))
                            },
                            placeholder = {
                                Text(
                                    text = "Паспорт - когда, кем выдан",
                                    color = Black
                                )
                            },
                            isError = state.documentDitailsError != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                    if (state.documentDitailsError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.documentDitailsError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    ColourButton(
                        Constans.ClientColorsList.clientColorsList, onColorSelected = {
                            viewModelClient.onFormEvent(ValidationFormEvent.ColorChanged(it))
                        }, state.color, false, ""
                    )
                }


                item {
                    OutlinedTextField(
                        value = state.members,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.MembersChanged(it))
                        },
                        placeholder = { Text(text = "Количество человек в тарифе", color = Black) },
                        isError = state.membersError != null,
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
                    if (state.membersError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.membersError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = state.overMembers,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.OverMembersChanged(it))
                        },
                        placeholder = { Text(text = "Количество человек на доп. места", color = Black) },
                        isError = state.overMembersError != null,
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
                    if (state.overMembersError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.overMembersError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = "c ${state.dateInString} по ${state.dateOutString} ",
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.InLongDateChanged(viewModelClient.dateInLong))
                            viewModelClient.onFormEvent(ValidationFormEvent.InStringDateChanged(viewModelClient.dateInString))
                            viewModelClient.onFormEvent(ValidationFormEvent.OutLongDateChanged(viewModelClient.dateOutLong))
                            viewModelClient.onFormEvent(ValidationFormEvent.OutStringDateChanged(viewModelClient.dateOutString))
                        },
                        placeholder = { Text(text = "Период проживания", color = Black) },
                        isError = (state.dateInStringError != null || state.dateOutStringError != null || state.dateInLongError != null || state.dateOutLongError != null),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                            .clickable {
                                navController.navigate(
                                    route =
                                    "${Routs.setClientPeriodFromAddClient}/$appatmentName"
                                )
//                                viewModel.showDatePickerDialog(context, "in")

                            },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
                        ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            disabledTextColor = Black,
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138),

                            ),
                        enabled = false,
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )
                    if (state.dateInStringError != null || state.dateOutStringError != null || state.dateInLongError != null || state.dateOutLongError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.dateInStringError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = state.prePayment,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.PrePaymentChanged(it))
                        },

                        placeholder = { Text(text = "Внесенный залог", color = Black) },
                        isError = state.prePaymentError != null,
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

                    if (state.prePaymentError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.prePaymentError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = state.payment,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.PaymentChanged(it))
                        },

                        placeholder = { Text(text = "Стоимость тарифа", color = Black) },
                        isError = state.paymentError != null,
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

                    if (state.paymentError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.paymentError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    OutlinedTextField(
                        value = state.overPayment,
                        onValueChange = {
                            viewModelClient.onFormEvent(ValidationFormEvent.OverPaymentChanged(it))
                        },

                        placeholder = { Text(text = "Стоимость доп. места", color = Black) },
                        isError = state.overPaymentError != null,
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

                    if (state.overPaymentError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = state.overPaymentError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                }


                item {
                    state.transferInfo?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModelClient.onFormEvent(ValidationFormEvent.transferInfoChanged(it))
                            },

                            placeholder = { Text(text = "Сведения для трансфера", color = Black) },
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
                }


                item {
                    state.referer?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModelClient.onFormEvent(ValidationFormEvent.refererChanged(it))
                            },
                            placeholder = { Text(text = "Рекламный ресурс", color = Black) },
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
                }


                item{
                    Spacer(modifier = Modifier.padding(10.dp))

                    Box(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(modifier = Modifier.padding(end = 80.dp),
                                onClick = {
                                    viewModelClient.getAppatmentClients(appatmentName)
                                    navController.navigate("${Routs.mainScreenClients}/${appatmentName}")
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
//                                    // Log.d("myTag", "аппат с представл - $appatmentName")
                                    viewModelClient.onFormEvent(ValidationFormEvent.onSubmitInsert(appatmentName))
                                }
                            )
                            {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_check_24),
                                    contentDescription = "Добавить клиента",
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
}


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