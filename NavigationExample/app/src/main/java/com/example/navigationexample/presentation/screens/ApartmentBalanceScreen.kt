package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.example.navigationexample.presentation.screens.common.bottomBorder


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
        Row() {
            DropdownButtonWithMultipleSelection()
        }

        Row {
            Card(
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxHeight(0.4f)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .border(
                        1.dp,
                        SolidColor(Color(223, 75, 0)),
                        shape = RoundedCornerShape(10.dp)
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
            verticalAlignment = Alignment.CenterVertically,

            ) {
            val gradientColors = listOf(Color(0xFFDF4B00), Color(0xFF292929))
            TabRow(
                selectedTabIndex = tabIndex.value!!,
                backgroundColor = Color(red = 41, green = 41, blue = 41),
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
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

        TableScreen(
            listOf(0.6f, 0.2f, 0.2f), listOf(
                listOf("sadfasdfasdf", "234234", "234234"),
            )
        )


    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor: Color
) {
    Text(
        text = text,
        Modifier
            .bottomBorder(1.dp, Color.Black)
            .weight(weight)
            .padding(10.dp),
        color = textColor,
    )
}

@Composable
fun TableScreen(weight: List<Float>, data: List<List<String>>) {
    // Just a fake data... a Pair of Int and String
//    val tableData = (1..100).mapIndexed { index, item ->
//        index to "Item $index"
//    }
    // Each cell of a column must have the same weight.
//    val column1Weight = .3f // 30%
//    val column2Weight = .7f // 70%
    // The LazyColumn will be our table. Notice the use of the weights below
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .border(
                1.dp,
                SolidColor(Color(223, 75, 0)),
                shape = RoundedCornerShape(10.dp)
            ),
    ) {
        // Here is the header
        item {
            Row(Modifier.background(color = Color(red = 41, green = 41, blue = 41))) {
                TableCell(text = "Клиент", weight = weight[0], Color(0xFFDF4B00))
                TableCell(text = "Расход", weight = weight[1], Color(0xFFDF4B00))
                TableCell(text = "Доход", weight = weight[2], Color(0xFFDF4B00))
            }
        }
        // Here are all the lines of your table.
        items(data) {
//            val (id, text) = it
            Row(Modifier.fillMaxWidth()) {
                TableCell(text = it[0], weight = weight[0], Color(0xFFBEBCBA))
                TableCell(text = it[1], weight = weight[1], Color(0xFFF11505))
                TableCell(text = it[1], weight = weight[2], Color(0xFF08E511))
            }
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
fun DropdownButtonWithMultipleSelection() {
    // Список элементов для выпадающего списка
    val items = listOf("Элемент 1", "Элемент 2", "Элемент 3", "Элемент 4")

    // Состояние для отслеживания выбранных элементов из списка
    val selectedItems: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }

    // Состояние для отслеживания раскрытия или сворачивания списка
    val isExpanded = remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.padding(5.dp)
    ) {
        // Кнопка для раскрытия или сворачивания списка

        GradientButton(
            buttonText = "Выберите обьекты",
            colors = listOf(
                Color(0xFF292929),
                Color(0xFFDF4B00),
                Color(0xFF292929),
            ),
        ) {
            isExpanded.value = !isExpanded.value
        }

        // Компонент списка
        if (isExpanded.value) {
            LazyColumn(
                modifier = Modifier.padding(top = 3.dp),
                contentPadding = PaddingValues(bottom = 3.dp)
            ) {
                // Пункты списка с названием элементов и чекбоксами
                items(items) { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = selectedItems.value.contains(item),
                            onCheckedChange = {
                                // Добавление или удаление элемента из выбранных
                                if (selectedItems.value.contains(item)) {
                                    selectedItems.value = selectedItems.value - item
                                } else {
                                    selectedItems.value = selectedItems.value + item
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = Color(0xFFDF4B00),  // Цвет для выбранного чекбокса
                                uncheckedColor = Color(0xFFBEBCBA), // Цвет для не выбранного чекбокса
                                checkmarkColor = Color(0xFFDF4B00) // Цвет для галочки внутри чекбокса (при выборе)
                            )
                        )
                        Text(text = item, modifier = Modifier.padding(start = 5.dp), color=Color(0xFFBEBCBA))
                    }
                }
            }
        }
    }
}