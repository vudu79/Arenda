package com.example.navigationexample.presentation.screens

import android.annotation.SuppressLint
import android.graphics.BlurMaskFilter.Blur
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.*
import com.example.navigationexample.ui.theme.Purple500


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun AddAppatmentScreen(
    navController: NavHostController,
    viewModel: AppatmentViewModel,

    onHome: () -> Unit
) {
    val context = LocalContext.current
//    val scaffoldState = rememberScaffoldState()
    val nameAppat = remember { mutableStateOf("") }
    val typeAppat = remember { mutableStateOf("") }
    val addressAppat = remember { mutableStateOf("") }
    val squearAppat = remember { mutableStateOf("") }


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
                .background(Color(R.color.back1))
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
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    OutlinedTextField(
                        value = nameAppat.value,
                        onValueChange = { nameAppat.value = it },
                        label = { Text(text = "Название", color = Black) },
                        placeholder = { Text(text = "Название", color = Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black,
                            textColor = Black,
                            backgroundColor = Color(R.color.inputBack)
                        )
                    )
                    OutlinedTextField(
                        value = typeAppat.value,
                        onValueChange = { typeAppat.value = it },
                        label = { Text(text = "Тип помещения", color = Black) },
                        placeholder = { Text(text = "Тип помещения", color = Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black
                        )
                    )
                    OutlinedTextField(
                        value = addressAppat.value,
                        onValueChange = { addressAppat.value = it },
                        label = { Text(text = "Адрес помещения", color = Black) },
                        placeholder = { Text(text = "Адрес помещения", color = Black) },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .fillMaxHeight(0.5f),
//                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black
                        )
                    )
                    OutlinedTextField(
                        value = squearAppat.value,
                        onValueChange = { squearAppat.value = it },
                        label = { Text(text = "Площадь", color = Black) },
                        placeholder = { Text(text = "Площадь", color = Black) },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            unfocusedBorderColor = Black, textColor = Black
                        )
                    )

                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth(0.8f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                        onClick = {
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
                                    "Введите площадь объекта!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                viewModel.insertAppatment(
                                    Appatment(
                                        nameAppat.value,
                                        addressAppat.value,
                                        typeAppat.value,
                                        squearAppat.value.toFloat()
                                    )
                                )
                                Toast.makeText(
                                    context,
                                    "Объект недвижимости добавлен!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                    ) {
                        Text(text = "Добавить", fontSize = 20.sp, color = Black)
                    }

                    Spacer(modifier = Modifier.padding(5.dp))

                    Text(text = "Назад", color = Black,
                        modifier = Modifier.clickable {
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

