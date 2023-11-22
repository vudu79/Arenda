package com.example.navigationexample.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.constants.SourceEvent
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientPaymentScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    clientPhone: String
) {
    var showCustomDialogPrePayment by remember {
        mutableStateOf(false)
    }

    var showCustomDialogPayment by remember {
        mutableStateOf(false)
    }

    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    val inputValidateState = viewModelClient.validateFormState
    val paymentDebt = viewModelClient.paymentDebt
    val clientDBState = viewModelClient.uiClientState
//    val (selectedOption, onOptionSelected) = remember { mutableStateOf(ClientStatus.statusList[0]) }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current



    LaunchedEffect(key1 = context) {
        viewModelClient.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.UpdateSuccess -> {
                    Toast.makeText(
                        context, "Данные по оплате обновлены!", Toast.LENGTH_SHORT
                    ).show()
//                    viewModelClient.getClient(clientPhone)
//                    viewModelClient.getClientState(clientPhone)
                    viewModelClient.resetClientStateForValidation()
                    viewModelClient.getAppatmentClients(currentAppatment!!.name)
                    viewModelClient.getClientState(clientPhone = clientPhone)
                    viewModelClient.getClientStateForValidation(clientPhone)

                    mainNavController.navigate("${Routs.clientPaymentScreen}/${clientPhone}")

//                    mainNavController.navigate(route = "${Routs.mainScreenClients}/${currentAppatment?.name}")
                }

                is ValidatAllFieldsResultEvent.UpdateWrong -> {
                    Log.d("myTag", "Содержание листа ошибок ${event.hasErrorList}")
                    Toast.makeText(
                        context, "Ошибка при обновлении клиента", Toast.LENGTH_SHORT
                    ).show()
//
                }

                is ValidatAllFieldsResultEvent.InsertSuccess -> {
                }
            }
        }
    }


    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(41, 41, 41))
    ) {

        Text(
            text = makeFullName(inputValidateState),
            maxLines = 2,
            modifier = Modifier
                .background(Color(41, 41, 41))
                .align(Alignment.CenterHorizontally)
                .padding(15.dp),
            fontSize = 26.sp,
            color = Color.White
        )

        Divider(
            color = inputValidateState.color, modifier = Modifier
                .fillMaxWidth()
                .width(10.dp)
        )


        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier
                .background(Color(red = 41, green = 41, blue = 41))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

//    оплата бронирования
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Оплата бронирования/предоплата  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 20.sp,
                            color = Color(223, 75, 0)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Должен - ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = clientDBState.value?.prePayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 19.sp,
                            color = Color(255, 255, 255, 255)
                        )


                        Text(
                            text = "Заплатил - ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = clientDBState.value?.completedPrePayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = if (viewModelClient.isPrePaymentComplete.value == true) Color(
                                9,
                                202,
                                17,
                                255
                            ) else Color(254, 253, 253, 255)
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    OutlinedTextField(
                        value = inputValidateState.completedPrePayment,
                        onValueChange = {
//                            prePaymentMoment = if (it == "") "0" else it
                            viewModelClient.onFormEvent(
                                ValidationFormEvent.CompletedPrePaymentChanged(
                                    it
                                )
                            )
                        },
                        enabled = !viewModelClient.isPrePaymentComplete.value!!,
                        placeholder = { Text(text = "Оплата бронирования", color = Color.Black) },
                        isError = inputValidateState.completedPrePaymentError != null,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 1.dp,
                                bottom = 5.dp,
                                start = 5.dp,
                                end = 5.dp
                            ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color(223, 75, 0),
                            textColor = Color.Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )

                    if (inputValidateState.completedPrePaymentError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = inputValidateState.completedPrePaymentError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    if (!viewModelClient.isPrePaymentComplete.value!!) {
                        GradientButton(
                            "Сохранить",
                            listOf(
                                Color(0xFF292929),
                                Color(0xFF0BDE13),
                                Color(0xFF292929),
                            ),

                            ) {
//                      viewModelClient.onFormEvent(
//                                ValidationFormEvent.PrePaymentChanged(
//                                    (inputValidateState.prePayment.toInt() - prePaymentMoment.toInt()).toString()
//                                )
//                            )
                            viewModelClient.onFormEvent(
                                ValidationFormEvent.onSubmitUpdate(
                                    SourceEvent.PAYMENTUPDATE
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(3.dp))

                    if (viewModelClient.isPrePaymentComplete.value!!) {
                        GradientButton(
                            "Очистить",
                            listOf(
                                Color(0xFF292929),
                                Color(0xFFF81403),
                                Color(0xFF292929),
                            ),
                        ) {
                            showCustomDialogPrePayment = !showCustomDialogPrePayment
                        }
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                }
            }
//    полная стоимость проживания
//            if (isPaymentActive) {
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Оплата стоимости проживания  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 20.sp,
                            color = Color(223, 75, 0)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                    ) {
                        Text(
                            text = "Должен - ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = paymentDebt.value.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 19.sp,
                            color = Color(255, 255, 255, 255)
                        )


                        Text(
                            text = "Заплатил - ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = clientDBState.value?.completedPayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = if (viewModelClient.isPaymentComplete.value == true) Color(
                                9,
                                202,
                                17,
                                255
                            ) else Color(255, 255, 255, 255)
                        )
                    }

                    Spacer(modifier = Modifier.padding(10.dp))

                    OutlinedTextField(
                        value = inputValidateState.completedPayment,
                        onValueChange = {
                            viewModelClient.onFormEvent(
                                ValidationFormEvent.CompletedPaymentChanged(
                                    it
                                )
                            )
                        },
                        enabled = viewModelClient.isPrePaymentComplete.value!! and !viewModelClient.isPaymentComplete.value!!,
                        placeholder = { Text(text = "Оплата клиента", color = Color.Black) },
                        isError = inputValidateState.completedPaymentError != null,
                        singleLine = false,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 1.dp,
                                bottom = 5.dp,
                                start = 5.dp,
                                end = 5.dp
                            ),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Black,
                            focusedBorderColor = Color(223, 75, 0),
                            textColor = Color.Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
                        ),
                        keyboardActions = KeyboardActions(onNext = {
                            focusManager.moveFocus(FocusDirection.Down)
                        }),
                    )

                    if (inputValidateState.completedPaymentError != null) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = inputValidateState.completedPaymentError!!,
                                color = MaterialTheme.colors.error,
                                modifier = Modifier.align(Alignment.BottomStart)
                            )
                        }
                    }
                    if (viewModelClient.isPrePaymentComplete.value!! and !viewModelClient.isPaymentComplete.value!!) {
                        GradientButton(
                            "Сохранить",
                            listOf(
                                Color(0xFF292929),
                                Color(0xFF0BDE13),
                                Color(0xFF292929),
                            ),
                        ) {
                            viewModelClient.onFormEvent(
                                ValidationFormEvent.onSubmitUpdate(
                                    SourceEvent.PAYMENTUPDATE
                                )
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(3.dp))
                    if ( viewModelClient.isPaymentComplete.value!!) {
                        GradientButton(
                            "Очистить", listOf(
                                Color(0xFF292929),
                                Color(0xFFF81403),
                                Color(0xFF292929),
                            )
                        ) {
                            showCustomDialogPayment = !showCustomDialogPayment
                        }
                    }
                    Spacer(modifier = Modifier.padding(15.dp))
                }
            }
//            }


//    полная стоимость брони
//            item {
//                Column(
//                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.Top,
//                        horizontalArrangement = Arrangement.Start,
//                    ) {
//                        Text(
//                            text = "Полная стоимость проживания ",
//                            maxLines = 1,
//                            modifier = Modifier
//                                .background(Color(41, 41, 41))
//                                .padding(start = 5.dp),
//                            fontSize = 19.sp,
//                            color = Color(223, 75, 0)
//                        )
//
//                        Text(
//                            text = totalCoast.toString(),
//                            maxLines = 1,
//                            modifier = Modifier
//                                .background(Color(41, 41, 41))
//                                .padding(start = 5.dp),
//
//                            fontSize = 18.sp,
//                            color = Color(254, 253, 253, 255)
//                        )
//                    }
//                    Spacer(modifier = Modifier.padding(10.dp))
//                }
//            }

//    полная стоимость брони с учетом залога
//            item {
//                Column(
//                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
//                ) {
//                    Row(
//                        verticalAlignment = Alignment.Top,
//                        horizontalArrangement = Arrangement.Start,
//                    ) {
//                        Text(
//                            text = "Стоимость с учетом залога ",
//                            maxLines = 1,
//                            modifier = Modifier
//                                .background(Color(41, 41, 41))
//                                .padding(start = 5.dp),
//                            fontSize = 19.sp,
//                            color = Color(223, 75, 0)
//                        )
//
//                        Text(
//                            text = (totalCoast - prepayment).toString(),
//                            maxLines = 1,
//                            modifier = Modifier
//                                .background(Color(41, 41, 41))
//                                .padding(start = 5.dp),
//
//                            fontSize = 18.sp,
//                            color = Color(254, 253, 253, 255)
//                        )
//                    }
//                    Spacer(modifier = Modifier.padding(10.dp))
//                }
//            }

            item {
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
                                viewModelClient.getAppatmentClients(currentAppatment!!.name)
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
                        IconButton(
                            onClick = {
//                                viewModelClient.onFormEvent(ValidationFormEvent.onSubmitUpdate(""))
                            }
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_check_24),
                                contentDescription = "Изменить данные клиента",
                                modifier = Modifier.size(55.dp),
                                tint = Color(223, 75, 0)
                            )
                        }
                    }
                }
            }
        }
    }

    if (showCustomDialogPrePayment) {
        CustomAlertDialog(onDismiss = {
            showCustomDialogPrePayment = !showCustomDialogPrePayment
        }, onOk = {
            showCustomDialogPrePayment = !showCustomDialogPrePayment
            viewModelClient.onFormEvent(
                ValidationFormEvent.CompletedPrePaymentChanged(
                    "0"
                )
            )
            viewModelClient.onFormEvent(ValidationFormEvent.onSubmitUpdate(SourceEvent.PAYMENTUPDATE))
            viewModelClient.setActivePrePaymentTextField()
        },
            message = "Вы уверены? Даные оплаты бронирования будут удалены."
        )
    }

    if (showCustomDialogPayment) {
        CustomAlertDialog(onDismiss = {
            showCustomDialogPayment = !showCustomDialogPayment
        }, onOk = {
            showCustomDialogPayment = !showCustomDialogPayment
            viewModelClient.onFormEvent(
                ValidationFormEvent.CompletedPaymentChanged(
                    "0"
                )
            )
            viewModelClient.onFormEvent(ValidationFormEvent.onSubmitUpdate(SourceEvent.PAYMENTUPDATE))
            viewModelClient.setActivePaymentYTextField()
        },
            message = "Вы уверены? Даные оплаты будут удалены."
        )
    }
}


fun makeFullName(state: ValidationFormState): String {
    val fName = state.firstName
    val sName = state.secondName ?: ""
    val lName = state.lastName ?: ""
    return "$fName $sName $lName"
}


//fun dateToString(longDate: Long?): String {
//    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
//    return longDate?.let { LocalDate.ofEpochDay(it).format(formatter) }.toString()
//}

//
//@Preview
//@Composable
//private fun ClientDitailsScreen() {
//    ClientDitailsScreen()
//}


@Composable
fun GradientButton(
    buttonText: String,
    colors: List<Color>,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            disabledBackgroundColor = Color(0xF4A6E7A8),
            disabledContentColor = Color.Gray,
        ),
//        enabled = isEnabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 1.dp,
                bottom = 5.dp,
                start = 5.dp,
                end = 5.dp
            )
            .clip(RoundedCornerShape(5.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = colors
                )
            ),
    ) {
        Text(
            text = buttonText, color = Color.White, modifier = Modifier.padding(10.dp)
        )
    }
}


@Composable
fun GradientOutlinedTextField(
    value: String, onValueChange: (String) -> Unit, placeholder: String = "Enter text"
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .sizeIn(
                minWidth = 0.dp, minHeight = 0.dp
            )
            .wrapContentWidth()
            .wrapContentHeight()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF292929),
                        Color(0xFFDF4B00),
                    )
                )
            )
            .padding(top = 5.dp),
        placeholder = { Text(text = placeholder, color = Color.Gray) },
        textStyle = TextStyle(fontSize = 14.sp, color = Color.White),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
    )


}
