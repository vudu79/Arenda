package com.example.navigationexample.presentation.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
//import com.example.composeex.ClientItemRow
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog
import com.example.navigationexample.presentation.screens.common.rand
import com.kizitonwose.calendar.sample.compose.clickable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Random


@Composable
fun AddScoresScreen(
) {
    val focusManager = LocalFocusManager.current
    val listMock = listOf<Pair<String, Color>>(
        Pair("Газ", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Вода", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Электроэнергия", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Квартплата", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Ремонт", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Ушерб", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Страховка", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
        Pair("Интернет", Color(red = rand(0, 255), rand(0, 255), rand(0, 255))),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color(red = 41, green = 41, blue = 41))
            .border(2.dp, color = Color.Gray),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color(red = 41, green = 41, blue = 41))
                .border(2.dp, color = Color.Gray),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            androidx.compose.material3.Text(
                text = "Размер", fontSize = 25.sp, color = Color(223, 75, 0),
                modifier = Modifier
                    .padding(10.dp)
            )
            OutlinedTextField(
                shape = RoundedCornerShape(20.dp),
                value = "",
                onValueChange = {},
                placeholder = { },
                isError = false,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp, start = 15.dp, end = 15.dp),
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
                },
                    ),
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(color = Color(red = 41, green = 41, blue = 41))
                .border(2.dp, color = Color.Gray),

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "Категория", fontSize = 25.sp, color = Color(223, 75, 0),
                modifier = Modifier
                    .padding(10.dp)
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .padding(3.dp)
            ) {
                items(listMock) { item ->
                    CategoryItemRow(
                        item.first, item.second
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.CategoryItemRow(
    categoryName: String, categoryColor: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end= 15.dp, top = 5.dp, bottom=5.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(categoryColor)
                .combinedClickable(
                    onClick = {
                    },
                    onLongClick = {
                    }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween

        )
        {
//            Box(
//                modifier = Modifier
//                    .fillMaxHeight()
//                    .padding(5.dp)
//                    .clickable {
//                    },
//            ) {
//                Image(
//                    painter = painterResource(R.drawable.baseline_phone_24),
//                    contentDescription = "asd",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .padding(top = 2.dp)
//                        .size(50.dp)
//                        .clip(CircleShape)
//                )
//            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(),
            ) {
                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = categoryName, fontSize = 20.sp, color = Color.Black,
                        modifier = Modifier
                            .padding(15.dp)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun AddScorePreview() {
    AddScoresScreen()
}