package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.CustomComponentsSample


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientDitailsScreen(
    mainNavController: NavHostController,
    viewModel: ClientViewModel,
    clientPhone: String
) {

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        viewModel.getClientState(clientPhone)
    }
    val state = viewModel.validateFormState
    var isStatusEditActive = remember {
        mutableStateOf(false)
    }


    Column(
        modifier = Modifier
            .background(Color(red = 41, green = 41, blue = 41))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

//        Статус _______________________________________________________________
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .background(Color(red = 41, green = 41, blue = 41)),
            shape = RoundedCornerShape(5.dp),
            elevation = 8.dp,
            onClick = {

            }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .border(width = 2.dp, color = Color(223, 75, 0))
                    .background(Color(red = 41, green = 41, blue = 41)),
            ) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(red = 41, green = 41, blue = 41))

                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Text(
                            text = state.status,
                            modifier = Modifier.align(Alignment.CenterStart).background(Color(1,1,1)),
                            fontSize = 25.sp,
                            color = Color(254, 253, 253, 255)
                        )

                        IconButton(
                            modifier = Modifier.align(Alignment.BottomEnd),
                            onClick = {
                                isStatusEditActive.value = !isStatusEditActive.value
                            }
                        )
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_edit_24),
                                contentDescription = "Редактировать",

                                modifier = Modifier.size(30.dp),
                                tint = Color(223, 75, 0)
                            )
                        }

                    }
                }

                Spacer(modifier = Modifier.padding(10.dp))
                if (isStatusEditActive.value) {
                    OutlinedTextField(
                        value = state.status,
                        onValueChange = {
                            viewModel.onFormEvent(ValidationFormEvent.StatusChanged(it))
                        },
                        placeholder = { Text(text = "Имя", color = Color.Black) },
//                isError = state.status != null,
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Color.Black,
                            textColor = Color.Black,
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
                }
            }

//            if (state.firstNameError != null) {
//                Text(
//                    text = state.firstNameError!!,
//                    color = MaterialTheme.colors.error,
//                    modifier = Modifier.align(Alignment.Start)
//                )
//            }
        }


//        Имя _______________________________________________________________
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = 8.dp,
            onClick = {

            }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(3.dp),
            ) {
                Text(
                    state.firstName,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )

                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = state.firstName,
                    onValueChange = {
                        viewModel.onFormEvent(ValidationFormEvent.FirstNameChanged(it))
                    },
                    placeholder = { Text(text = "Имя", color = Color.Black) },
                    isError = state.firstNameError != null,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Black,
                        textColor = Color.Black,
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
                        text = state.firstName!!,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }
            }
        }


//        Фамилия _______________________________________________________________
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = 8.dp,
            onClick = {

            }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(3.dp),
            ) {
                Text(
                    text = state.secondName!!,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )

                Spacer(modifier = Modifier.padding(10.dp))
                OutlinedTextField(
                    value = state.secondName!!,
                    onValueChange = {
                        viewModel.onFormEvent(ValidationFormEvent.SecondNameChanged(it))
                    },
                    placeholder = { Text(text = "Имя", color = Color.Black) },
                    isError = state.secondNameError != null,
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = Color.Black,
                        textColor = Color.Black,
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
                if (state.secondNameError != null) {
                    Text(
                        text = state.secondName!!,
                        color = MaterialTheme.colors.error,
                        modifier = Modifier.align(Alignment.Start)
                    )
                }

            }
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
                    mainNavController.navigate(Routs.mainScreenClients)
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
                    contentDescription = "Обновить",

                    modifier = Modifier.size(55.dp),
                    tint = Color(223, 75, 0)
                )
            }
        }
    }


//    IconButton(onClick = {
//        mainNavController.navigate(Routs.addAppatmentScreen)
//    }) {
//        Icon(
//            painter = painterResource(id = R.drawable.baseline_add_home_work_24),
//            contentDescription = "Добавить объект",
//
//            modifier = Modifier
//                .size(50.dp)
//                .padding(bottom = 3.dp),
//            tint = Color(223, 75, 0)
//
//        )
//    }
}

