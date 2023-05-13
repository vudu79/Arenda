package com.example.navigationexample.presentation.screens


import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue

import android.annotation.SuppressLint
import android.util.Log
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
import androidx.compose.material.MaterialTheme.colors
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
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(
    navController: NavHostController,
    viewModel: AppatmentViewModel,
    appatmentName: String,
) {

    val currentAppatment by viewModel.currentApartment.observeAsState()
    val focusManager = LocalFocusManager.current
    val state = viewModel.validateFormState

    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.Success -> {
                    Log.d("myTag", "payment --- ${state.payment}")
                    Log.d("myTag", "prePayment --- ${state.prePayment}")
                    Log.d("myTag", "members --- ${state.members}")

                    Toast.makeText(
                        context, "Новый клиент зарегестрирован!", Toast.LENGTH_SHORT
                    ).show()
                    navController.navigate(route = "${Routs.mainScreenClients}?appatment_name=$appatmentName")
//
                }
            }
        }
    }

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
                        isError = state.firstNameError != null,
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
                            imeAction = ImeAction.Next, keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )
                    if (state.firstNameError != null) {
                        Text(
                            text = state.firstNameError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    state.secondName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.SecondNameChanged(it))
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
                        Text(
                            text = state.secondNameError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    state.lastName?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.LastNameChanged(it))
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
                        Text(
                            text = state.lastNameError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    PhoneField(
                        state.phone,
                        placeHolder = "Контактный телефон",
                        mask = "+7(000)-000-00-00",
                        maskNumber = '0',
                        onPhoneChanged = {
                            viewModel.onFormEvent(ValidationFormEvent.PhoneChanged(it))

                        },
                        errorMessage = state.phoneError,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    state.documentNamber?.let {
                        PhoneField(
                            value = it,
                            placeHolder = "Паспорт: серия и номер",
                            mask = "0000-000000",
                            maskNumber = '0',
                            onPhoneChanged = { phone ->
                                viewModel.onFormEvent(
                                    ValidationFormEvent.DocumentNamberChanged(
                                        phone
                                    )
                                )
                            },
                            errorMessage = state.documentNamberError,
                            modifier = Modifier.align(Alignment.Start)
                        )
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
                        Text(
                            text = state.documentDitailsError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    ColourButton(
                        Constans.ClientColorsList.clientColorsList, onColorSelected = {
                            viewModel.onFormEvent(ValidationFormEvent.ColorChanged(it))
                        }, state.color
                    )

                    OutlinedTextField(
                        value = state.members,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.MembersChanged(it))
                        },
                        placeholder = { Text(text = "Количество человек", color = Black) },
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
                        Text(
                            text = state.membersError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    OutlinedTextField(
                        value = "c ${state.dateInString} по ${state.dateOutString} ",
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.InLongDateChanged(viewModel.dateInLong))
                            viewModel.onFormEvent(ValidationFormEvent.InStringDateChanged(viewModel.dateInString))
                            viewModel.onFormEvent(ValidationFormEvent.OutLongDateChanged(viewModel.dateOutLong))
                            viewModel.onFormEvent(ValidationFormEvent.OutStringDateChanged(viewModel.dateOutString))
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
                                    "${Routs.setClientPeriod}?appatment_name=$appatmentName"
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
                        Text(
                            text = state.dateInStringError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    OutlinedTextField(
                        value = state.prePayment,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.PrepaymentChanged(it))
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
                        Text(
                            text = state.prePaymentError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    OutlinedTextField(
                        value = state.payment,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.PaymentChanged(it))
                        },

                        placeholder = { Text(text = "Стоимость суток", color = Black) },
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
                        Text(
                            text = state.paymentError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }

                    state.transferInfo?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.transferInfoChanged(it))
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


                    state.referer?.let {
                        OutlinedTextField(
                            value = it,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.refererChanged(it))
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