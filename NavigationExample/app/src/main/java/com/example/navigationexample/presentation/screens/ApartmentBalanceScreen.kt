package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ApartmentBalanceScreen(
    viewModelBalance: BalanceViewModel,

//    mainNavController: NavHostController,
//    viewModelClient: ClientViewModel,
//    viewModelAppatment: AppatmentViewModel,
//    clientPhone: String
) {
    val tabIndex = viewModelBalance.tabIndex.observeAsState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(red = 41, green = 41, blue = 41))

    ) {
        Row {
            Card(
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(10.dp),
                shape = RoundedCornerShape(10.dp),
                elevation = 8.dp,
                onClick = {}

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(red = 41, green = 41, blue = 41))
                        .border(
                            1.dp, Color(223, 75, 0)
                        )
                ) {
                    Text(text = "Ballance", fontSize = 20.sp)
                }
            }
        }



        TabRow(selectedTabIndex = tabIndex.value!!,
            backgroundColor = Color(red = 41, green = 41, blue = 41),

            modifier = Modifier
                .fillMaxHeight(0.1f)
                .padding(vertical = 1.dp, horizontal = 1.dp)
                .clip(RoundedCornerShape(20)),
            indicator = { tabPositions: List<TabPosition> ->
                Box {}
            }

        ) {
            viewModelBalance.tabs.forEachIndexed { index, title ->
                val selected = tabIndex.value!! == index
                Tab(
                    modifier = if (selected) Modifier
                        .border(2.dp, Color(223, 75, 0))

                        .clip(RoundedCornerShape(20))
                        .background(
                            Color.Gray
                        )
                    else Modifier
                        .clip(RoundedCornerShape(20))
                        .background(
                            Color(red = 41, green = 41, blue = 41),
                        ),

                    text = { Text(title, fontSize = 14.sp, color = Color.White, modifier = Modifier
                        .padding(bottom = 10.dp)


                    ) },
                    selected = tabIndex.value!! == index,
                    onClick = { viewModelBalance.updateTabIndex(index) },
                    icon = {}
//                        when (index) {
//                            0 -> Icon(imageVector = Icons.Default.Home, contentDescription = null)
//                            1 -> Icon(imageVector = Icons.Default.Info, contentDescription = null)
//                        }
//                    }
                )
            }
        }

        when (tabIndex.value) {
            0 -> HomeScreen(viewModel = viewModelBalance)
            1 -> AboutScreen(viewModel = viewModelBalance)
        }
    }
}

@Composable
fun HomeScreen(viewModel: BalanceViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(red = 41, green = 41, blue = 41))

            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipe()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "Welcome Home!",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun AboutScreen(viewModel: BalanceViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(red = 41, green = 41, blue = 41))

            .draggable(
                state = viewModel.dragState.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipe()
                }),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Text(
                text = "About",
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}