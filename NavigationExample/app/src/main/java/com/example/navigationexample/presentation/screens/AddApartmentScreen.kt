package com.example.navigationexample.presentation.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddApartmentScreen(
    navController: NavHostController,
    apartmentViewModel: ApartmentViewModel,
    onHome: () -> Unit
) {
    val context = LocalContext.current
    val nameAppat = remember { mutableStateOf("") }
    val typeAppat = remember { mutableStateOf("Квартира") }
    val rentatypeAppat = remember { mutableStateOf("Посуточно") }
    val addressAppat = remember { mutableStateOf("") }
    val squearAppat = remember { mutableStateOf("") }
    val numberRooms = remember { mutableStateOf("") }
    val numberBeds = remember { mutableStateOf("") }

    val expanded = remember { mutableStateOf(false) }
    val items = listOf("Аппартаменты", "Квартира", "Комерческое")
    val disabledValue = "B"
    val selectedIndex = remember { mutableStateOf(0) }

    val expandedRenta = remember { mutableStateOf(false) }
    val itemsRenta = listOf("Помесячно", "Посуточно", "Почасовая")
    val disabledValueRenta = "B"
    val selectedIndexRenta = remember { mutableStateOf(0) }


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

//        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
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
                    text = "Новый объект",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    color = Color.White


                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = nameAppat.value,
                        onValueChange = { nameAppat.value = it },
//                        label = { Text(text = "Название", color = Black, fontSize = 18.sp) },
                        placeholder = { Text(text = "Название", color = Black, fontSize = 18.sp) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.79f)
                            .padding(bottom = 10.dp, top = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        elevation = 30.dp,
                    )
                    {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(142, 143, 138)
                                ),
                            contentAlignment = Alignment.CenterStart,


                            ) {
                            Text(
                                text = "Тип объекта- ${items[selectedIndex.value]}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, top = 15.dp)
//                                .padding(top = 5.dp, start = 5.dp)
                                    .height(45.dp)
                                    .clickable(onClick = { expanded.value = true })
                                    .background(
                                        Color(142, 143, 138)
                                    ),
                                fontSize = 18.sp,
                                color = Black,
                                textAlign = TextAlign.Justify
                            )

                        }

                        MaterialTheme(
                            shapes = MaterialTheme.shapes.copy(
                                medium = RoundedCornerShape(
                                    10.dp
                                )
                            )
                        ) {
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
                                        typeAppat.value = s
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

                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.79f),
                        shape = RoundedCornerShape(12.dp),
                        elevation = 30.dp,
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(
                                    Color(142, 143, 138)
                                ),
                            contentAlignment = Alignment.CenterStart,
                        ) {
                            Text(
                                text = "Срок аренды- ${itemsRenta[selectedIndexRenta.value]}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 15.dp, start = 10.dp)
                                    .height(45.dp)
                                    .clickable(onClick = { expandedRenta.value = true })
                                    .background(
                                        Color(142, 143, 138)
                                    ),
                                fontSize = 18.sp,
                                color = Black,

                                )
                        }
                        MaterialTheme(
                            shapes = MaterialTheme.shapes.copy(
                                medium = RoundedCornerShape(
                                    10.dp
                                )
                            )
                        ) {
                            DropdownMenu(
                                expanded = expandedRenta.value,
                                onDismissRequest = { expandedRenta.value = false },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color.Transparent
                                    )
                            ) {
                                itemsRenta.forEachIndexed { index, s ->
                                    DropdownMenuItem(onClick = {
                                        selectedIndexRenta.value = index
                                        expandedRenta.value = false
                                        rentatypeAppat.value = s
                                    }) {
                                        val disabledText = if (s == disabledValueRenta) {
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

                    OutlinedTextField(
                        value = addressAppat.value,
                        onValueChange = { addressAppat.value = it },
//                        label = { Text(text = "Адрес помещения", color = Black, fontSize = 18.sp) },
                        placeholder = {
                            Text(
                                text = "Адрес помещения",
                                color = Black,
                                fontSize = 18.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.3f)
                            .padding(top = 10.dp),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = numberRooms.value,
                        onValueChange = { numberRooms.value = it },
//                        label = { Text(text = "Площадь", color = Black, fontSize = 18.sp) },
                        placeholder = {
                            Text(
                                text = "Количество комнат",
                                color = Black,
                                fontSize = 18.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = numberBeds.value,
                        onValueChange = { numberBeds.value = it },
//                        label = { Text(text = "Площадь", color = Black, fontSize = 18.sp) },
                        placeholder = {
                            Text(
                                text = "Количество спальных мест",
                                color = Black,
                                fontSize = 18.sp
                            )
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )

                    OutlinedTextField(
                        value = squearAppat.value,
                        onValueChange = { squearAppat.value = it },
//                        label = { Text(text = "Площадь", color = Black, fontSize = 18.sp) },
                        placeholder = { Text(text = "Площадь", color = Black, fontSize = 18.sp) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .padding(top = 10.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black,
                            backgroundColor = Color(142, 143, 138)
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
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


                        IconButton(onClick = {
                            if (nameAppat.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите название объекта недвижимости!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (addressAppat.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите адрес объекта!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (squearAppat.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите площадь объекта!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (typeAppat.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите тип объекта!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (rentatypeAppat.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите срок аренды!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (numberBeds.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите количество спальных мест",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else if (numberRooms.value.isEmpty()) {
                                Toast.makeText(
                                    context,
                                    "Введите количество комнат",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                apartmentViewModel.insertApartment(
                                    Apartment(
                                        nameAppat.value,
                                        addressAppat.value,
                                        typeAppat.value,
                                        rentatypeAppat.value,
                                        numberRooms.value.toInt(),
                                        numberBeds.value.toInt(),
                                        squearAppat.value.toFloat()
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    "Объект недвижимости добавлен!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            apartmentViewModel.getAllApartments()
                            navController.navigate(Routs.allApartmentsScreen)
                        })
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

//@Composable
//fun DropdownDemo(): String{
//
//}