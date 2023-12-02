package com.example.navigationexample.presentation.screens

import android.util.Log
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationexample.R
import com.example.navigationexample.presentation.screens.common.bottomBorder


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ApartmentBalanceScreen(
    viewModelBalance: BalanceViewModel,
    viewModelApartment: ApartmentViewModel,
    apartmentName: String
//    mainNavController: NavHostController,
//    viewModelClient: ClientViewModel,
//    clientPhone: String
) {

    val gradientColors = listOf(Color(0xFFDF4B00), Color(0xFF292929))

    val tabIndexPeriod = viewModelBalance.tabIndexPeriod.observeAsState()
    val tabIndexExpenses = viewModelBalance.tabIndexExpenses.observeAsState()

    val itemsApartments by viewModelBalance.allApartments.observeAsState(listOf())
//    val currentApart by viewModelBalance.currentApartment.observeAsState()
    val selectedApartments: MutableState<List<String>> = remember { mutableStateOf(emptyList()) }

    Log.d("MyTag", "Лист -  $itemsApartments")
//    Log.d("MyTag", "Текуший -  ${currentApart}")

    val isExpanded = remember {
        mutableStateOf(false)
    }


//главная колонка
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(red = 41, green = 41, blue = 41))

    ) {
// ряд с блоком апартаментов
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
                .border(
                    1.dp,
                    SolidColor(Color(223, 75, 0)),
                    shape = RoundedCornerShape(10.dp)
                ),
        ) {
            Column(
                modifier = Modifier.padding(5.dp)
            ) {

                Row {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(.8f)
                            .padding(3.dp)
                    ) {
                        selectedApartments.value.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = it,
                                    color = Color(0xFFBEBCBA),
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }
                        }
                    }
                }

                if (isExpanded.value) {
                    LazyColumn(
                        modifier = Modifier.padding(top = 3.dp),
                        contentPadding = PaddingValues(bottom = 1.dp)
                    ) {
                        // Пункты списка с названием элементов и чекбоксами
                        items(itemsApartments) { item ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = selectedApartments.value.contains(item.name),
                                    onCheckedChange = {
                                        // Добавление или удаление элемента из выбранных
                                        if (selectedApartments.value.contains(item.name)) {
                                            selectedApartments.value =
                                                selectedApartments.value - item.name
                                        } else {
                                            selectedApartments.value =
                                                selectedApartments.value + item.name
                                        }
                                    },
                                    colors = CheckboxDefaults.colors(
                                        checkedColor = Color(0xFFDF4B00),  // Цвет для выбранного чекбокса
                                        uncheckedColor = Color(0xFFBEBCBA), // Цвет для не выбранного чекбокса
                                        checkmarkColor = Color(0xFFDF4B00) // Цвет для галочки внутри чекбокса (при выборе)
                                    )
                                )
                                Text(
                                    text = item.name,
                                    modifier = Modifier.padding(start = 5.dp),
                                    color = Color(0xFFBEBCBA)
                                )
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(2.dp))
            Box(
                modifier = Modifier
                    .width(40.dp)
                    .height(40.dp)
            ) {
                IconButton(
                    onClick = {
                        isExpanded.value = !isExpanded.value
                    }
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = "Добавить объект",
                        modifier = Modifier.size(50.dp),
                        tint = Color(223, 75, 0)
                    )
                }
            }
        }


//        ряд с вкладками Доходы и Расходы
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            TabRow(
                selectedTabIndex = tabIndexExpenses.value!!,
                backgroundColor = Color(red = 41, green = 41, blue = 41),
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                viewModelBalance.tabsExpenses.forEachIndexed { indexExpenses, titleExpenses ->
                    val isSelectedExpenses = indexExpenses == tabIndexExpenses.value!!
                    val gColor =
                        if (indexExpenses == 0) gradientColors else gradientColors.reversed()

                    val backgroundShaderExpenses =
                        if (isSelectedExpenses) Brush.horizontalGradient(gColor) else SolidColor(
                            Color.Transparent
                        )
                    Tab(
                        selected = isSelectedExpenses,
                        onClick = { viewModelBalance.updateTabIndexExpenses(indexExpenses) },
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .height(40.dp)
                            .padding(horizontal = 3.dp)
                            .background(
                                brush = backgroundShaderExpenses,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = titleExpenses,
                            color = if (isSelectedExpenses) Color.White else Color.Gray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }
//        ряд с центральным блоком (Card) балланса и цифрой расхода или дохода
        Row {
            when (tabIndexExpenses.value) {
                0 -> ExpensesCard("Доходы")
                1 -> ExpensesCard("Расходы")
            }
        }

// ряд с вкладками периодов выборки
        Row(
            verticalAlignment = Alignment.CenterVertically,

            ) {
            TabRow(
                selectedTabIndex = tabIndexPeriod.value!!,
                backgroundColor = Color(red = 41, green = 41, blue = 41),
                modifier = Modifier
                    .padding(start = 5.dp, end = 5.dp)
            ) {
                viewModelBalance.tabsPeriod.forEachIndexed { index, title ->
                    val isSelectedPeriod = index == tabIndexPeriod.value!!
                    val backgroundShaderPeriod =
                        if (isSelectedPeriod) Brush.horizontalGradient(gradientColors) else SolidColor(
                            Color.Transparent
                        )
                    Tab(
                        selected = isSelectedPeriod,
                        onClick = { viewModelBalance.updateTabIndexPeriod(index) },
                        modifier = Modifier
                            .padding(vertical = 3.dp)
                            .height(40.dp)
                            .padding(horizontal = 3.dp)
                            .background(
                                brush = backgroundShaderPeriod,
                                shape = RoundedCornerShape(8.dp)
                            )
                    ) {
                        Text(
                            text = title,
                            color = if (isSelectedPeriod) Color.White else Color.Gray,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
            }
        }

// экраны для списков расходов и доходов
        when (tabIndexPeriod.value) {
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
                state = viewModel.dragStatePeriod.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipePeriod()
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
                state = viewModel.dragStatePeriod.value!!,
                orientation = Orientation.Horizontal,
                onDragStarted = { },
                onDragStopped = {
                    viewModel.updateTabIndexBasedOnSwipePeriod()
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

        Row {
            Column(
                modifier = Modifier
                    .fillMaxWidth(.8f)
            ) {
                selectedItems.value.forEach {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(it)
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth(.2f)
            ) {
                IconButton(
                    onClick = {
                        isExpanded.value = !isExpanded.value
                    }
                )
                {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_add_circle_24),
                        contentDescription = "Добавить объект",
                        modifier = Modifier.size(30.dp),
                        tint = Color(223, 75, 0)
                    )
                }
            }
            Spacer(modifier = Modifier.padding(2.dp))
        }
    }
//        GradientButton(
//            buttonText = "Выберите обьекты",
//            colors = listOf(
//                Color(0xFF292929),
//                Color(0x80DF4B00),
//                Color(0xFF292929),
//            ),
//        ) {
//            isExpanded.value = !isExpanded.value
//        }

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
                    Text(
                        text = item,
                        modifier = Modifier.padding(start = 5.dp),
                        color = Color(0xFFBEBCBA)
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpensesCard(text: String) {
    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .fillMaxHeight(0.3f)
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
            Text(text = text, fontSize = 20.sp, color = Color.White)
        }
    }
}