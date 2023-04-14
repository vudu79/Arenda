package com.example.navigationexample.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddClientScreen(
    navController: NavHostController,
    viewModel: AppatmentViewModel,
    appatmentName:String,

) {
    val context = LocalContext.current
//    val scaffoldState = rememberScaffoldState()
    val name = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val members = remember { mutableStateOf("") }
    val inDate = remember { mutableStateOf("") }
    val outDate = remember { mutableStateOf("") }
    val payment = remember { mutableStateOf("") }
    val prepayment = remember { mutableStateOf("") }
    val sity = remember { mutableStateOf("") }


//    Image(
//        painter = painterResource(
//            id = R.drawable.sky
//        ),
//        contentDescription = "im1",
//        modifier = Modifier
//            .fillMaxSize()
//            .alpha(0.5f),
//        contentScale = ContentScale.FillBounds
//    )
    Box(
        modifier = Modifier
            .fillMaxSize(),

        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(41, 41, 41))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.TopCenter
            ) {
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(30.dp))

                    .padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Новый клиент",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    color = Color.White


                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = name.value,
                        onValueChange = { name.value = it },
                        label = { Text(text = "ФИО", color = Black) },
                        placeholder = { Text(text = "ФИО", color = Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        )
                    )
                    OutlinedTextField(
                        value = phone.value,
                        onValueChange = { phone.value = it },
                        label = { Text(text = "Контактный телефон", color = Black) },
                        placeholder = { Text(text = "Контактный телефон", color = Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        )
                    )
                    OutlinedTextField(
                        value = members.value,
                        onValueChange = { members.value = it },
                        label = { Text(text = "Количество человек", color = Black) },
                        placeholder = { Text(text = "Количество человек", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.5f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        )
                    )
//                    OutlinedTextField(
//                        value = inDate.value,
//                        onValueChange = { inDate.value = it },
//                        label = { Text(text = "Дата прибытия", color = Black) },
//                        placeholder = { Text(text = "Дата прибытия", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black, textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        )
//                    )

//                    OutlinedTextField(
//                        value = outDate.value,
//                        onValueChange = { outDate.value = it },
//                        label = { Text(text = "Дата убытия", color = Black) },
//                        placeholder = { Text(text = "Дата убытия", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black, textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        )
//                    )

//                    OutlinedTextField(
//                        value = payment.value,
//                        onValueChange = { payment.value = it },
//                        label = { Text(text = "Стоимость суток", color = Black) },
//                        placeholder = { Text(text = "Стоимость суток", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black, textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        )
//                    )


//
//                    OutlinedTextField(
//                        value = prepayment.value,
//                        onValueChange = { prepayment.value = it },
//                        label = { Text(text = "Внесенный залог", color = Black) },
//                        placeholder = { Text(text = "Внесенный залог", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black, textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        )
//                    )


//                    OutlinedTextField(
//                        value = sity.value,
//                        onValueChange = { sity.value = it },
//                        label = { Text(text = "Внесенный залог", color = Black) },
//                        placeholder = { Text(text = "Внесенный залог", color = Black) },
//                        singleLine = true,
//                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
//                        colors = TextFieldDefaults.outlinedTextFieldColors(
//                            unfocusedBorderColor = Black, textColor = Black,
//                            backgroundColor = Color(142, 143, 138)
//                        )
//                    )


                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color(223, 75, 0)),
                        onClick = {
                            if (name.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите ФИО клиента!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (phone.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите контатный телефон клиента!",
                                    Toast.LENGTH_SHORT
                                ).show()
//                            }
//                            else if (inDate.value.isEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Введите дату прибытия!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
//                            } else if (outDate.value.isEmpty()) {
//                                Toast.makeText(
//                                    context,
//                                    "Введите дату убытия!",
//                                    Toast.LENGTH_SHORT
//                                ).show()
                            } else {
                                viewModel.addClient(
                                    Client(name.value,
                                        phone.value,0,0,
                                        appatmentName)
                                )
                                Toast.makeText(
                                    context,
                                    "Объект недвижимости добавлен!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            viewModel.getAppatmentClients(appatmentName)
                            navController.navigate(route = "${Routs.mainScreenClients}?appatment_name=$appatmentName")
                        }

                    ) {
                        Text(text = "Добавить", fontSize = 20.sp, color = Black)
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(text = "Назад", color = Color.White,
                        modifier = Modifier.clickable {
                            viewModel.getAppatmentClients(appatmentName)
                            navController.navigate(Routs.home)

                        }
                    )
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

