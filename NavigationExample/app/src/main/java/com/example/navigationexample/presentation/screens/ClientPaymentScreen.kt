package com.example.navigationexample.presentation.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormState
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.ColourButton
import com.example.navigationexample.presentation.screens.common.PhoneField
import java.time.LocalDate
import java.time.format.DateTimeFormatter


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientPaymentScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    clientPhone: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()

    val state = viewModelClient.validateFormState

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(ClientStatus.statusList[0]) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = context) {
        viewModelClient.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.UpdateSuccess -> {
                    Toast.makeText(
                        context, "Клиент обновлен!", Toast.LENGTH_SHORT
                    ).show()
                    viewModelClient.getAppatmentClients(currentAppatment!!.name)
                    mainNavController.navigate(route = "${Routs.mainScreenClients}/${currentAppatment?.name}")
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
            text = makeFullName(state),
            maxLines = 2,
            modifier = Modifier
                .background(Color(41, 41, 41))
                .align(Alignment.CenterHorizontally)
                .padding(15.dp),
            fontSize = 26.sp,
            color = Color.White
        )

        Divider(
            color = state.color, modifier = Modifier
                .fillMaxWidth()
                .width(5.dp)
        )


        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier
                .background(Color(red = 41, green = 41, blue = 41))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

//    залог
            item {
                Column(
                    horizontalAlignment = Alignment.Start, modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Text(
                            text = "Внесенный залог/предоплата  ",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 19.sp,
                            color = Color(223, 75, 0)
                        )

                        Text(
                            text = state.prePayment.toString(),
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),

                            fontSize = 18.sp,
                            color = Color(254, 253, 253, 255)
                        )

                    }
                    Spacer(modifier = Modifier.padding(10.dp))

                    OutlinedTextField(
                        value = state.prePayment,
                        onValueChange = {
                            viewModelClient.onFormEvent(
                                ValidationFormEvent.PrePaymentChanged(
                                    it
                                )
                            )
                        },
                        placeholder = { Text(text = "Залог", color = Color.Black) },
                        isError = state.prePaymentError != null,
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
                            textColor = Color.Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.None,
                            autoCorrect = true,
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
            }

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
                        IconButton(
                            onClick = {
                                viewModelClient.onFormEvent(ValidationFormEvent.onSubmitUpdate(""))
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
}


fun makeFullName(state: ValidationFormState): String {
    val fName = state.firstName ?: ""
    val sName = state.secondName ?: ""
    val lName = state.lastName ?: ""
    return "$fName $sName $lName"
}


fun dateToString(longDate: Long?): String {
    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
    return longDate?.let { LocalDate.ofEpochDay(it).format(formatter) }.toString()
}

//
//@Preview
//@Composable
//private fun ClientDitailsScreen() {
//    ClientDitailsScreen()
//}


@Composable
fun GradientButton(buttonText: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            disabledBackgroundColor = Color.LightGray,
            disabledContentColor = Color.Gray,
        ),
        modifier = Modifier
            .padding(top = 5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFF292929),
                        Color(0xFFDF4B00),
                    )
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
