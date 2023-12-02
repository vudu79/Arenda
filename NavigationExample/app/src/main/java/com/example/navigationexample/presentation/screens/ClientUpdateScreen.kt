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
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.constants.Constans
import com.example.navigationexample.constants.SourceEvent
import com.example.navigationexample.domain.models.ClientStatus
import com.example.navigationexample.domain.usecase.validation.ValidatAllFieldsResultEvent
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.ColourButton
import com.example.navigationexample.presentation.screens.common.PhoneField


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientUpdateScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: ApartmentViewModel,
    clientPhone: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()

    val state = viewModelClient.validateFormState

    val (selectedOption, onOptionSelected) = remember { mutableStateOf(ClientStatus.statusList[0]) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    val isStatusEditActive = remember {
        mutableStateOf(false)
    }
    val fieldStatusHeight = remember { mutableStateOf(50) }
    fieldStatusHeight.value = when (isStatusEditActive.value) {
        true -> 180
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
        true -> {
            if (state.documentDitails != "") 240 else 170
        }
        false -> {
            if (state.documentDitails != "") 120 else 50
        }
    }
    val isMembersEditActive = remember {
        mutableStateOf(false)
    }

    val fieldMembersHeight = remember { mutableStateOf(50) }
    fieldMembersHeight.value = when (isMembersEditActive.value) {
        true -> 120
        false -> 50
    }


    val isOverMembersEditActive = remember {
        mutableStateOf(false)
    }

    val fieldOverMembersHeight = remember { mutableStateOf(50) }
    fieldOverMembersHeight.value = when (isOverMembersEditActive.value) {
        true -> 120
        false -> 50
    }

    val isPrepaymentEditActive = remember {
        mutableStateOf(false)
    }
    val fieldPrepaymentHeight = remember { mutableStateOf(50) }
    fieldPrepaymentHeight.value = when (isPrepaymentEditActive.value) {
        true -> 120
        false -> 50
    }

    val isPaymentEditActive = remember {
        mutableStateOf(false)
    }
    val fieldPaymentHeight = remember { mutableStateOf(50) }
    fieldPaymentHeight.value = when (isPaymentEditActive.value) {
        true -> 120
        false -> 50
    }

    val isOverPaymentEditActive = remember {
        mutableStateOf(false)
    }
    val fieldOverPaymentHeight = remember { mutableStateOf(50) }
    fieldOverPaymentHeight.value = when (isOverPaymentEditActive.value) {
        true -> 120
        false -> 50
    }

    LaunchedEffect(key1 = context) {
        viewModelClient.validationEvents.collect { event ->
            when (event) {
                is ValidatAllFieldsResultEvent.UpdateSuccess -> {
                    Toast.makeText(
                        context, "Клиент обновлен!", Toast.LENGTH_SHORT
                    ).show()
                    viewModelClient.getApartmentClients(currentAppatment!!.name)
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
                Row() {
                    Text(
                        text = "Статус клиента",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))

                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))

                                ) {
                                    Text(
                                        text = state.status,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(41, 41, 41))
                                            .padding(start = 5.dp),

                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    modifier = Modifier
//                                    .align(Alignment.BottomEnd)
                                        .padding(start = 10.dp, end = 10.dp),
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

                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .selectableGroup()
                                    .fillMaxWidth()
                            ) {
                                ClientStatus.statusList.forEach { text ->
                                    Row(
                                        Modifier
                                            .height(25.dp)
                                            .selectable(
                                                selected = (text == selectedOption),
                                                onClick = { onOptionSelected(text)
                                                    viewModelClient.onFormEvent(
                                                        ValidationFormEvent.StatusChanged(
                                                            text
                                                        )
                                                    )
                                                }
                                            ),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        RadioButton(
                                            selected = (text == selectedOption),
                                            onClick = null
                                        )
                                        Text(
                                            text = text,
                                            fontSize = 12.sp,
                                            color = Color(254, 253, 253, 255)
                                        )
                                    }
                                }
                            }

//                    if (isStatusEditActive.value) {
//                        OutlinedTextField(
//                            value = state.status,
//                            onValueChange = {
//                                viewModel.onFormEvent(ValidationFormEvent.StatusChanged(it))
//                            },
//                            placeholder = { Text(text = "Имя", color = Color.Black) },
////                isError = state.status != null,
//                            singleLine = false,
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(top = 1.dp, bottom = 5.dp, start = 5.dp, end = 5.dp),
//                            colors = TextFieldDefaults.outlinedTextFieldColors(
//                                unfocusedBorderColor = Color.Black,
//                                textColor = Color.Black,
//                                backgroundColor = Color(142, 143, 138)
//                            ),
//                            keyboardOptions = KeyboardOptions(
//                                imeAction = ImeAction.Next, keyboardType = KeyboardType.Text,
//                                capitalization = KeyboardCapitalization.None,
//                                autoCorrect = true,
//                            ),
//                            keyboardActions = KeyboardActions(onNext = {
//                                focusManager.moveFocus(FocusDirection.Down)
//                            }),
//                        )
//                    }
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
            }
        }


//        Имя+++++++++++++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Имя клиента",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.firstName,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(41, 41, 41))
                                            .padding(start = 5.dp),

                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
//                            modifier = Modifier.align(Alignment.BottomEnd),
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

                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isFirstNameEditActive.value) {
                                OutlinedTextField(
                                    value = state.firstName,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.FirstNameChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = { Text(text = "Имя", color = Color.Black) },
                                    isError = state.firstNameError != null,
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
            }
        }

//        Отчество +++++++++++++++++++++++++++++++++++++++++++++++

        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Отчество",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.secondName ?: "не установлено",
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isSecondNameEditActive.value) {
                                OutlinedTextField(
                                    value = state.secondName ?: "не установлено",
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.SecondNameChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = { Text(text = "Отчество", color = Color.Black) },
                                    isError = state.secondNameError != null,
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
            }
        }


//        Фамилия +++++++++++++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Фамилия",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.lastName ?: "не установлено",
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isLastNameEditActive.value) {
                                OutlinedTextField(
                                    value = state.lastName ?: "не установлено",
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.LastNameChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = { Text(text = "Фамилия", color = Color.Black) },
                                    isError = state.lastNameError != null,
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
            }
        }


//        Телефон +++++++++++++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Контактный телефон",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.phone,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isPhoneEditActive.value) {
                                PhoneField(
                                    value = state.phone,
                                    placeHolder = "Контактный телефон",
                                    mask = "000-000-00-00",
                                    maskNumber = '0',
                                    onPhoneChanged = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.PhoneChanged(
                                                it
                                            )
                                        )
                                    },
                                    errorMessage = state.phoneError,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                            }
                        }
                    }
                }
            }
        }


//        Номер паспорта +++++++++++++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Номер паспорта",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.documentNamber ?: "не установлено",
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isDocumentNamberEditActive.value =
                                            !isDocumentNamberEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isDocumentNamberEditActive.value) {
                                PhoneField(
                                    value = state.documentNamber ?: "не установлено",
                                    placeHolder = "Паспорт: серия номер",
                                    mask = "0000-000000",
                                    maskNumber = '0',
                                    onPhoneChanged = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.DocumentNamberChanged(
                                                it
                                            )
                                        )
                                    },
                                    errorMessage = state.documentNamberError,
                                    modifier = Modifier.align(Alignment.Start)
                                )
                            }
                        }
                    }
                }
            }
        }


//        паспортные данные+++++++++++++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Паспортные данные",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier
                                .fillMaxWidth(0.70f)
                                .height(fieldDocumentDitailsHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.documentDitails ?: "не установлено",
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(41, green = 41, blue = 41))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255),
                                        overflow = TextOverflow.Ellipsis,
                                        maxLines = 4,
                                        textAlign = TextAlign.Justify
                                    )
                                }

                                IconButton(
//                            modifier = Modifier.align(Alignment.BottomEnd),
                                    onClick = {
                                        isDocumentDitailsEditActive.value =
                                            !isDocumentDitailsEditActive.value
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

                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isDocumentDitailsEditActive.value) {
                                OutlinedTextField(
                                    value = state.documentDitails ?: "не установлено",
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.DocumentDitailsChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Паспор: кем и когда выдан",
                                            color = Color.Black
                                        )
                                    },
                                    isError = state.documentDitailsError != null,
                                    singleLine = false,

                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(120.dp)
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
                                    minLines = 4
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
            }
        }
// цвет+++++++++++++++++++++++++++++++++++++
        item {
            ColourButton(
                colors = Constans.ClientColorsList.clientColorsList,
                onColorSelected = {
                    viewModelClient.onFormEvent(ValidationFormEvent.ColorChanged(it))
                },
                currently = state.color,
                isUpdateScreen = true,
                name = state.firstName,
            )
        }

//_____________________________________________________________________________________________________
// даты+++++++++++++++++++++++++++++++++++++
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Даты проживания",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
//                                    Text(
//                                        text = "с ${state.dateInString} по ${state.dateOutString}",
//
//                                        maxLines = 1,
//                                        modifier = Modifier
//                                            .align(Alignment.CenterStart)
//                                            .background(Color(red = 41, 41, blue = 41))
//                                            .padding(start = 5.dp),
//                                        fontSize = 18.sp,
//                                        color = Color(254, 253, 253, 255)
//                                    )

                                    OutlinedTextField(
                                        value = "c ${state.dateInString} по ${state.dateOutString} ",
                                        onValueChange = {
                                            viewModelClient.onFormEvent(
                                                ValidationFormEvent.InLongDateChanged(
                                                    viewModelClient.dateInLong
                                                )
                                            )
                                            viewModelClient.onFormEvent(
                                                ValidationFormEvent.InStringDateChanged(
                                                    viewModelClient.dateInString
                                                )
                                            )
                                            viewModelClient.onFormEvent(
                                                ValidationFormEvent.OutLongDateChanged(
                                                    viewModelClient.dateOutLong
                                                )
                                            )
                                            viewModelClient.onFormEvent(
                                                ValidationFormEvent.OutStringDateChanged(
                                                    viewModelClient.dateOutString
                                                )
                                            )
                                        },
                                        placeholder = {
                                            Text(
                                                text = "Период проживания",
                                                color = Color.Black
                                            )
                                        },
                                        isError = (state.dateInStringError != null || state.dateOutStringError != null || state.dateInLongError != null || state.dateOutLongError != null),
                                        singleLine = true,
                                        modifier = Modifier
                                            .fillMaxWidth()
//                                            .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                                            .clickable {
//
                                            },
                                        keyboardOptions = KeyboardOptions(
                                            imeAction = ImeAction.Next,
                                            keyboardType = KeyboardType.Number
                                        ),
                                        colors = TextFieldDefaults.outlinedTextFieldColors(
                                            disabledTextColor = Color.White,
                                            unfocusedBorderColor = Color.Black,
                                            textColor = Color.Black,
                                            backgroundColor = Color(red = 41, 41, blue = 41),

                                            ),
                                        enabled = false,
                                        keyboardActions = KeyboardActions(onNext = {
                                            focusManager.moveFocus(FocusDirection.Down)
                                        }),
                                    )


                                }
                                IconButton(
                                    onClick = {
                                        mainNavController.navigate(
                                            route =
                                            "${Routs.setClientPeriodFromEditClient}/${clientPhone}"
                                        )
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
//                            Spacer(modifier = Modifier.padding(2.dp))
//                            if (isLastNameEditActive.value) {
//                                OutlinedTextField(
//                                    value = state.lastName ?: "не установлено",
//                                    onValueChange = {
//                                        viewModelClient.onFormEvent(
//                                            ValidationFormEvent.LastNameChanged(
//                                                it
//                                            )
//                                        )
//                                    },
//                                    placeholder = { Text(text = "Фамилия", color = Color.Black) },
//                                    isError = state.lastNameError != null,
//                                    singleLine = false,
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .padding(
//                                            top = 1.dp,
//                                            bottom = 5.dp,
//                                            start = 5.dp,
//                                            end = 5.dp
//                                        ),
//                                    colors = TextFieldDefaults.outlinedTextFieldColors(
//                                        unfocusedBorderColor = Color.Black,
//                                        textColor = Color.Black,
//                                        backgroundColor = Color(142, 143, 138)
//                                    ),
//                                    keyboardOptions = KeyboardOptions(
//                                        imeAction = ImeAction.Next,
//                                        keyboardType = KeyboardType.Text,
//                                        capitalization = KeyboardCapitalization.None,
//                                        autoCorrect = true,
//                                    ),
//                                    keyboardActions = KeyboardActions(onNext = {
//                                        focusManager.moveFocus(FocusDirection.Down)
//                                    }),
//                                )
//                            }
                        }
//                        if (state.lastNameError != null) {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxWidth()
//                            ) {
//                                Text(
//                                    text = state.lastNameError!!,
//                                    color = MaterialTheme.colors.error,
//                                    modifier = Modifier.align(Alignment.CenterStart)
//                                )
//                            }
//                        }
                    }
                }
            }
        }


//        item {
//            OutlinedTextField(
//                value = "c ${state.dateInString} по ${state.dateOutString} ",
//                onValueChange = {
//                    viewModelClient.onFormEvent(ValidationFormEvent.InLongDateChanged(viewModelClient.dateInLong))
//                    viewModelClient.onFormEvent(ValidationFormEvent.InStringDateChanged(viewModelClient.dateInString))
//                    viewModelClient.onFormEvent(ValidationFormEvent.OutLongDateChanged(viewModelClient.dateOutLong))
//                    viewModelClient.onFormEvent(ValidationFormEvent.OutStringDateChanged(viewModelClient.dateOutString))
//                },
//                placeholder = { Text(text = "Период проживания", color = Color.Black) },
//                isError = (state.dateInStringError != null || state.dateOutStringError != null || state.dateInLongError != null || state.dateOutLongError != null),
//                singleLine = true,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
//                    .clickable {
//                        navController.navigate(
//                            route =
//                            "${Routs.setClientPeriod}/$appatmentName"
//                        )
////                                viewModel.showDatePickerDialog(context, "in")
//
//                    },
//                keyboardOptions = KeyboardOptions(
//                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Number
//                ),
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    disabledTextColor = Color.Black,
//                    unfocusedBorderColor = Color.Black, textColor = Color.Black,
//                    backgroundColor = Color(142, 143, 138),
//
//                    ),
//                enabled = false,
//                keyboardActions = KeyboardActions(onNext = {
//                    focusManager.moveFocus(FocusDirection.Down)
//                }),
//            )
//            if (state.dateInStringError != null || state.dateOutStringError != null || state.dateInLongError != null || state.dateOutLongError != null) {
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                ) {
//                    Text(
//                        text = state.dateInStringError!!,
//                        color = MaterialTheme.colors.error,
//                        modifier = Modifier.align(Alignment.BottomStart)
//                    )
//                }
//            }
//        }

//  ______________________________________________________________________________________________________

// количестов человек
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Количество гостей",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .height(fieldMembersHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.members,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isMembersEditActive.value = !isMembersEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isMembersEditActive.value) {
                                OutlinedTextField(
                                    value = state.members,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.MembersChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Количество человек",
                                            color = Color.Black
                                        )
                                    },
                                    isError = state.membersError != null,
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
                            }
                        }
                        if (state.membersError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = state.membersError!!,
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }
            }
        }

// количество человек в тарифе
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Человек в тарифе за сутки",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .height(fieldOverMembersHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.overMembers,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isOverMembersEditActive.value = !isOverMembersEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isOverMembersEditActive.value) {
                                OutlinedTextField(
                                    value = state.overMembers,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.OverMembersChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Человек в тарифе за сутки",
                                            color = Color.Black
                                        )
                                    },
                                    isError = state.overMembersError != null,
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
                            }
                        }
                        if (state.overMembersError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = state.overMembersError!!,
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }
            }
        }

// предоплата
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Залог/Предоплата",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .height(fieldPrepaymentHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.prePayment,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isPrepaymentEditActive.value = !isPrepaymentEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isPrepaymentEditActive.value) {
                                OutlinedTextField(
                                    value = state.prePayment,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.PrePaymentChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Предоплата",
                                            color = Color.Black
                                        )
                                    },
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
                            }
                        }
                        if (state.prePaymentError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = state.prePaymentError!!,
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }
            }
        }

// цена за сутки
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Стоимость тарифа",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .height(fieldPaymentHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.pricePerDay,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isPaymentEditActive.value = !isPaymentEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isPaymentEditActive.value) {
                                OutlinedTextField(
                                    value = state.pricePerDay,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.PricePerDayChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Цена за сутки",
                                            color = Color.Black
                                        )
                                    },
                                    isError = state.pricePerDayError != null,
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
                            }
                        }
                        if (state.pricePerDayError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = state.pricePerDayError!!,
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
                }
            }
        }

// стоимотсь доп места
        item {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row() {
                    Text(
                        text = "Стоимость доп места",
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 12.sp,
                        color = Color(223, 75, 0)
                    )
                }
                Row() {
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
                                .height(fieldOverPaymentHeight.value.dp)
                                .border(
                                    width = 1.dp,
                                    color = Color(223, 75, 0),
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .background(Color(red = 41, 41, blue = 41)),
                        ) {

                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(Color(red = 41, green = 41, blue = 41))
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(0.85f)
                                        .height(50.dp)
                                        .background(Color(red = 41, green = 41, blue = 41))
                                ) {
                                    Text(
                                        text = state.overPayment,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .align(Alignment.CenterStart)
                                            .background(Color(1, 1, 1))
                                            .padding(start = 5.dp),
                                        fontSize = 18.sp,
                                        color = Color(254, 253, 253, 255)
                                    )
                                }
                                IconButton(
                                    onClick = {
                                        isOverPaymentEditActive.value = !isOverPaymentEditActive.value
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
                            Spacer(modifier = Modifier.padding(2.dp))
                            if (isOverPaymentEditActive.value) {
                                OutlinedTextField(
                                    value = state.overPayment,
                                    onValueChange = {
                                        viewModelClient.onFormEvent(
                                            ValidationFormEvent.OverPaymentChanged(
                                                it
                                            )
                                        )
                                    },
                                    placeholder = {
                                        Text(
                                            text = "Стоимость доп места",
                                            color = Color.Black
                                        )
                                    },
                                    isError = state.overPaymentError != null,
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
                            }
                        }
                        if (state.overPaymentError != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = state.overPaymentError!!,
                                    color = MaterialTheme.colors.error,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                        }
                    }
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
                            viewModelClient.onFormEvent(ValidationFormEvent.onSubmitUpdate(SourceEvent.CLIENTUPDATE))
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