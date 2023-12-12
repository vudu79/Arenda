package com.example.navigationexample.presentation.screens

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationexample.presentation.screens.common.rand
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun AddScoresScreen(
) {
    val context = LocalContext.current
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
            .background(color = Color(red = 41, green = 41, blue = 41)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 25.dp, end = 25.dp)
                .background(color = Color(red = 41, green = 41, blue = 41))
//                .border(2.dp, color = Color.Gray)
            ,

            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Divider(
//                color = Color(223, 75, 0),
                modifier = Modifier
                    .fillMaxWidth()  //fill the max height
                    .height(5.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF292929),
                                Color(0xFFF81403),
                                Color(0xFF292929),
                            )
                        )
                    )
            )
            Text(
                text = "Размер", fontSize = 20.sp, color = Color(223, 75, 0),
                modifier = Modifier
                    .padding(top = 10.dp, end = 10.dp)
            )
            OutlinedTextField(
                shape = RoundedCornerShape(25.dp),
                value = "",
                onValueChange = {},
                placeholder = { },
                isError = false,
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(223, 75, 0).copy(alpha = ContentAlpha.disabled),
                    unfocusedBorderColor = Color(223, 75, 0).copy(alpha = ContentAlpha.high),
                    disabledBorderColor = Color(223, 75, 0).copy(alpha = ContentAlpha.medium),
                    textColor = Color.Black,
                    backgroundColor = Color(red = 41, green = 41, blue = 41)
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next, keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.None,
                    autoCorrect = true,
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        focusManager.moveFocus(FocusDirection.Down)
                    },
                ),
            )
            Divider(
//                color = Color(223, 75, 0),
                modifier = Modifier
                    .fillMaxWidth()  //fill the max height
                    .height(5.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF292929),
                                Color(0xFFF81403),
                                Color(0xFF292929),
                            )
                        )
                    )
            )
            Text(
                text = "Категория", fontSize = 20.sp, color = Color(223, 75, 0),
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 10.dp)
            )

            customPagerView(listMock)

//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                EndlessHorizontalPager(
//                    items = listOf("A", "B", "C"),
//                    itemClicked = {
//                        Toast.makeText(
//                            context,
//                            "item clicked: $it",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    })
//            }


//            LazyColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .fillMaxHeight(0.38f)
//                    .padding(bottom = 20.dp)
//            ) {
//                items(listMock) { item ->
//                    CategoryItemRow(
//                        item.first, item.second
//                    )
//                }
//            }

            Button(
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(red = 41, green = 41, blue = 41),
                    contentColor = Color(223, 75, 0),
                ),

                shape = RoundedCornerShape(20.dp),
                modifier = Modifier
                    .fillMaxWidth()

                    .padding(bottom = 20.dp,top = 20.dp),
                border = BorderStroke(1.dp, Color(223, 75, 0)),
                contentPadding = PaddingValues(10.dp),
                onClick = { /*TODO*/ }

            ) {
                Text(text = "Добавить категорию", fontSize = 20.sp)
            }

            Divider(
//                color = Color(223, 75, 0),
                modifier = Modifier
                    .fillMaxWidth()  //fill the max height
                    .height(5.dp)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFF292929),
                                Color(0xFFF81403),
                                Color(0xFF292929),
                            )
                        )
                    )
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryItemRow(
    categoryName: String, categoryColor: Color
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 5.dp, bottom = 5.dp),
        shape = RoundedCornerShape(25.dp),
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
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .height(50.dp)
//                        .height(IntrinsicSize.Min)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = categoryName, fontSize = 20.sp, color = Color.Black,
                        modifier = Modifier
                            .padding(5.dp)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun customPagerView(list: List<Pair<String, Color>>) {
    val pageState = rememberPagerState()


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            HorizontalPager(
                count = Int.MAX_VALUE,
                contentPadding = PaddingValues(horizontal = 1.dp),
                state = pageState,
                itemSpacing = 5.dp,
                modifier = Modifier
                    .background(Color(41, green = 41, blue = 41))
            ) { page ->
                list.getOrNull(
                    (page % list.size)
                )?.let { item ->
                    CategoryItemRow(item.first, item.second)
                }
            }
        }
    }


    LaunchedEffect(key1 = Unit, block = {
        var initPage = Int.MAX_VALUE /2
        while (initPage % list.size !=0){
            initPage++
        }
        pageState.scrollToPage(initPage)
    })


    LaunchedEffect(key1 = pageState.currentPage, block = {
        launch {
            while (true){
                delay(2500)
                withContext(NonCancellable){
                    if(pageState.currentPage + 1 in 0..Int.MAX_VALUE){
                        pageState.animateScrollToPage(pageState.currentPage +1)
                    } else{
                        val initPage = Int.MAX_VALUE /2
                        pageState.scrollToPage(initPage)

                    }
                }
            }
        }
    })
}



//
//@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
//@Composable
//fun EndlessHorizontalPager(
//    items: List<String>,
//    itemClicked: (String) -> Unit
//) {
//    val pageCount = Int.MAX_VALUE
//    val pagerState = rememberPagerState(
//        initialPage = pageCount / 2
//    )
//
//    HorizontalPager(
//        modifier = Modifier
//            .background(Color(0xff469597))
//            .size(200.dp)
//            .clickable {
//                val clickedItem = items[pagerState.currentPage % items.size]
//                itemClicked(clickedItem)
//            },
//        count = pageCount,
//        state = pagerState
//    ) { page ->
//        Box(
//            modifier = Modifier.fillMaxSize(),
//            contentAlignment = Alignment.Center
//        ) {
//            Text(
//                text = items[page % items.size],
//                style = MaterialTheme.typography.h1
//            )
//        }
//    }
//}



@Preview
@Composable
fun AddScorePreview() {
    AddScoresScreen()
}