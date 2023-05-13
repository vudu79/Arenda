package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.screens.common.PhoneField


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

    val isStatusEditActive = remember {
        mutableStateOf(false)
    }
    val fieldStatusHeight = remember { mutableStateOf(50) }
    fieldStatusHeight.value = when (isStatusEditActive.value) {
        true -> 120
        false -> 50
    }

    val isFirstNameEditActive = remember {
        mutableStateOf(false)
    }
    val fieldFirstNameHeight = remember { mutableStateOf(50) }
    fieldFirstNameHeight.value = when (isFirstNameEditActive.value) {
        true -> 120
        false -> 50
    }

    val isSecondNameEditActive = remember {
        mutableStateOf(false)
    }
    val fieldSecondNameHeight = remember { mutableStateOf(50) }
    fieldSecondNameHeight.value = when (isSecondNameEditActive.value) {
        true -> 120
        false -> 50
    }

    val isLastNameEditActive = remember {
        mutableStateOf(false)
    }
    val fieldLastNameHeight = remember { mutableStateOf(50) }
    fieldLastNameHeight.value = when (isLastNameEditActive.value) {
        true -> 120
        false -> 50
    }


    val isPhoneEditActive = remember {
        mutableStateOf(false)
    }
    val fieldPhoneHeight = remember { mutableStateOf(50) }
    fieldPhoneHeight.value = when (isPhoneEditActive.value) {
        true -> 120
        false -> 50
    }

    val isDocumentNamberEditActive = remember {
        mutableStateOf(false)
    }
    val fieldDocumentNamberHeight = remember { mutableStateOf(50) }
    fieldDocumentNamberHeight.value = when (isDocumentNamberEditActive.value) {
        true -> 120
        false -> 50
    }

    val isDocumentDitailsEditActive = remember {
        mutableStateOf(false)
    }
    val fieldDocumentDitailsHeight = remember { mutableStateOf(50) }
    fieldDocumentDitailsHeight.value = when (isDocumentDitailsEditActive.value) {
        true -> 240
        false -> 120
    }



    LazyColumn(
        modifier = Modifier
            .background(Color(red = 41, green = 41, blue = 41))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,

        ) {

//        Статус _______________________________________________________________
        item {
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

                        .height(fieldStatusHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))

                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)


                        ) {
                            Text(
                                text = state.status,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),

                                fontSize = 18.sp,
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

                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isStatusEditActive.value) {
                        OutlinedTextField(
                            value = state.status,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.StatusChanged(it))
                            },
                            placeholder = { Text(text = "Имя", color = Color.Black) },
//                isError = state.status != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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
        }


//        Имя+++++++++++++++++++++++++++++++++++++++++++++++


        item {
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
                        .height(fieldFirstNameHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.firstName,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),

                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isFirstNameEditActive.value = !isFirstNameEditActive.value
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

                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isFirstNameEditActive.value) {
                        OutlinedTextField(
                            value = state.firstName,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.FirstNameChanged(it))
                            },
                            placeholder = { Text(text = "Имя", color = Color.Black) },
                            isError = state.firstNameError != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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
        }

//        Отчество +++++++++++++++++++++++++++++++++++++++++++++++

        item {
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
                        .height(fieldSecondNameHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.secondName!!,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),
                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )
                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isSecondNameEditActive.value = !isSecondNameEditActive.value
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
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isSecondNameEditActive.value) {
                        OutlinedTextField(
                            value = state.secondName!!,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.SecondNameChanged(it))
                            },
                            placeholder = { Text(text = "Отчество", color = Color.Black) },
                            isError = state.secondNameError != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                if (state.secondNameError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = state.secondNameError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }
            }
        }


//        Фамилия +++++++++++++++++++++++++++++++++++++++++++++++
        item {
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
                        .height(fieldLastNameHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.lastName!!,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),
                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isLastNameEditActive.value = !isLastNameEditActive.value
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
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isLastNameEditActive.value) {
                        OutlinedTextField(
                            value = state.lastName!!,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.LastNameChanged(it))
                            },
                            placeholder = { Text(text = "Фамилия", color = Color.Black) },
                            isError = state.lastNameError != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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
                if (state.lastNameError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = state.lastNameError!!,
                            color = MaterialTheme.colors.error,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }
                }
            }
        }


//        Телефон +++++++++++++++++++++++++++++++++++++++++++++++
        item {
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
                        .height(fieldPhoneHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.phone,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),
                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isPhoneEditActive.value = !isPhoneEditActive.value
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
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isPhoneEditActive.value) {
                        PhoneField(
                            value = state.phone,
                            placeHolder = "Контактный телефон",
                            mask = "+7(000)-000-00-00",
                            maskNumber = '0',
                            onPhoneChanged = {
                                viewModel.onFormEvent(ValidationFormEvent.PhoneChanged(it))
                            },
                            errorMessage = state.phoneError,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                }
            }
        }


//        Номер паспорта +++++++++++++++++++++++++++++++++++++++++++++++
        item {
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
                        .height(fieldDocumentNamberHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.documentNamber!!,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),
                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isDocumentNamberEditActive.value = !isDocumentNamberEditActive.value
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
                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isDocumentNamberEditActive.value) {
                        PhoneField(
                            value = state.documentNamber!!,
                            placeHolder = "Контактный телефон",
                            mask = "+7(000)-000-00-00",
                            maskNumber = '0',
                            onPhoneChanged = {
                                viewModel.onFormEvent(ValidationFormEvent.DocumentNamberChanged(it))
                            },
                            errorMessage = state.documentNamberError,
                            modifier = Modifier.align(Alignment.Start)
                        )
                    }
                }
            }
        }


//        Имя+++++++++++++++++++++++++++++++++++++++++++++++


        item {
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
                        .height(fieldDocumentDitailsHeight.value.dp)
                        .border(width = 2.dp, color = Color(223, 75, 0))
                        .background(Color(red = 41, 41, blue = 41)),
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(red = 41, green = 41, blue = 41))
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(50.dp)
                                .background(Color(red = 41, green = 41, blue = 41))
                                .heightIn(min = 60.dp, max = 200.dp)
                        ) {
                            Text(
                                text = state.documentDitails!!,
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .background(Color(1, 1, 1))
                                    .padding(start = 5.dp),

                                fontSize = 18.sp,
                                color = Color(254, 253, 253, 255)
                            )

                            IconButton(
                                modifier = Modifier.align(Alignment.BottomEnd),
                                onClick = {
                                    isDocumentDitailsEditActive.value = !isDocumentDitailsEditActive.value
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

                    Spacer(modifier = Modifier.padding(2.dp))
                    if (isFirstNameEditActive.value) {
                        OutlinedTextField(
                            value = state.documentDitails,
                            onValueChange = {
                                viewModel.onFormEvent(ValidationFormEvent.DocumentDitailsChanged(it))
                            },
                            placeholder = { Text(text = "Имя", color = Color.Black) },
                            isError = state.documentDitailsError != null,
                            singleLine = false,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
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
    }