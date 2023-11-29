package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
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
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(
                        1.dp, Color(223, 75, 0)
                    ),

                elevation = 8.dp,
                onClick = {}

            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(red = 41, green = 41, blue = 41))

                ) {
                    Text(text = "Ballance", fontSize = 20.sp)
                }
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val gradientColors = listOf(Color(0xFFDF4B00), Color(0xFF292929))
            TabRow(
                selectedTabIndex = tabIndex.value!!,
                backgroundColor = Color(red = 41, green = 41, blue = 41),
            ) {
                viewModelBalance.tabs.forEachIndexed { index, title ->
                    val isSelected = index == tabIndex.value!!
                    val backgroundShader =
                        if (isSelected) Brush.horizontalGradient(gradientColors) else SolidColor(
                            Color.Transparent
                        )
                    Tab(
                        selected = isSelected,
                        onClick = { viewModelBalance.updateTabIndex(index) },
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .height(40.dp)
                            .padding(horizontal = 3.dp)
                            .background(brush = backgroundShader, shape = RoundedCornerShape(8.dp))
                    ) {
                        Text(
                            text = title,
                            color = if (isSelected) Color.White else Color.Gray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
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

@Composable
fun CustomTabRowIndicator(
    tabPositions: List<TabPosition>,
    selectedTabIndex: Int
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp)
    ) {
        Divider(
            color = Color(223, 75, 0),
            modifier = Modifier
                .height(2.dp)
                .align(Alignment.BottomStart)
                .width(tabPositions[selectedTabIndex].width)
                .offset(x = tabPositions[selectedTabIndex].left)
        )
    }
}
