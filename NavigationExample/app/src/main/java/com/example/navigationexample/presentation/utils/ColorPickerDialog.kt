package com.example.navigationexample.presentation.utils

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigationexample.R

@Composable
fun ColourButton(
    colors: List<Color>,
    onColorSelected: (Color) -> Unit,
    currently: Color,
    isUpdateScreen: Boolean,
    name: String
) {
    var colorPickerOpen by rememberSaveable { mutableStateOf(false) }
    var currentlySelected by rememberSaveable(saver = colourSaver()) { mutableStateOf(currently) }

    if (isUpdateScreen) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Row() {
                Text(
                    text = "Цвет клиента в календаре",
                    maxLines = 1,
                    modifier = Modifier
                        .background(Color(41, 41, 41))
                        .padding(start = 5.dp),
                    fontSize = 12.sp,
                    color = Color(223, 75, 0)
                )
            }
            Box(
                modifier = Modifier
                    .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                    .background(Color(red = 41, 41, blue = 41))
                    .fillMaxSize()
                    .clip(RoundedCornerShape(5))
                    .border(
                        width = 1.dp,
                        color = Color(223, 75, 0),
                        shape = RoundedCornerShape(5.dp),
                    )
                    .clickable {
//                        colorPickerOpen = true
                    }

            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(1.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = name,
                        maxLines = 1,
                        modifier = Modifier
                            .background(Color(41, 41, 41))
                            .padding(start = 5.dp),
                        fontSize = 20.sp,
                        color = currentlySelected
                    )


//                    Canvas(
//                        modifier = Modifier
//                            .size(30.dp)
//                            .clip(RoundedCornerShape(5))
//                            .border(
//                                1.dp,
//                                MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
//                                RoundedCornerShape(20)
//                            )
//                            .background(currentlySelected)
//                            .clickable {
////                            colorPickerOpen = true
//                            },
//
//                    ) {}


                    IconButton(
                        onClick = {
                            colorPickerOpen = true
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
            }
        }


    } else {
        Box(
            modifier = Modifier
                .padding(bottom = 5.dp, start = 5.dp, end = 5.dp)
                .background(Color(red = 142, 143, 138))
                .fillMaxWidth()
                .clip(RoundedCornerShape(5))

                .clickable {
                    colorPickerOpen = true
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(start = 3.dp),
                    text = "Цвет для календаря",
                    color = Color.Black,
                    fontSize = 16.sp

                )

                Canvas(
                    modifier = Modifier
                        .size(30.dp)
                        .clip(RoundedCornerShape(5))
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                            RoundedCornerShape(20)
                        )
                        .background(currentlySelected)
                        .clickable {
                            colorPickerOpen = true
                        }
                ) {}
            }
        }
    }




    if (colorPickerOpen) {
        TwoColorDialog(
            colorList = colors,
            onDismiss = { colorPickerOpen = false },
            currentlySelected = currentlySelected,
            onColorSelected = {
                currentlySelected = it
                onColorSelected(it)
            }
        )
    }
}


@Composable
private fun ColorDialog(
    colorList: List<Color>,
    onDismiss: (() -> Unit),
    currentlySelected: Color,
    onColorSelected: ((Color) -> Unit) // when the save button is clicked
) {
    val gridState = rememberLazyGridState()

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                state = gridState
            ) {
                items(colorList) { color ->
                    var borderWidth = 0.dp
                    if (currentlySelected == color) {
                        borderWidth = 2.dp
                    }

                    Canvas(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            borderWidth,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                            RoundedCornerShape(20.dp)
                        )
                        .background(color)
                        .requiredSize(70.dp)
                        .clickable {
                            onColorSelected(color)
                            onDismiss()
                        }
                    ) {
                    }
                }
            }
        },
        confirmButton = {}
    )
}

@Composable
private fun TwoColorDialog(
    colorList: List<Color>,
    onDismiss: (() -> Unit),
    currentlySelected: Color,
    onColorSelected: ((Color) -> Unit)
) {
    val gridState = rememberLazyGridState()

    AlertDialog(
        shape = RoundedCornerShape(20.dp),
        containerColor = MaterialTheme.colorScheme.background,
        titleContentColor = MaterialTheme.colorScheme.outline,
        onDismissRequest = onDismiss,
        text = {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                state = gridState
            ) {
                items(colorList) { color ->
                    var borderWidth = 0.dp
                    if (currentlySelected == color) {
                        borderWidth = 2.dp
                    }

                    Canvas(modifier = Modifier
                        .padding(16.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .border(
                            borderWidth,
                            MaterialTheme.colorScheme.onBackground.copy(alpha = 0.75f),
                            RoundedCornerShape(20.dp)
                        )
//                        .background(color)
                        .requiredSize(70.dp)
                        .clickable {
                            onColorSelected(color)
                            onDismiss()
                        }
                    ) {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        drawPath(Path().apply {
                            moveTo(0f, 0f)
                            lineTo(canvasWidth, 0f)
                            lineTo(0f, canvasHeight)
                            close()
                        }, color = color)

                        drawPath(Path().apply {
                            moveTo(canvasWidth, 0f)
                            lineTo(0f, canvasHeight)
                            lineTo(canvasWidth, canvasHeight)
                            close()
                        }, color = color.copy(alpha = 0.6f))

                    }
                }
            }
        },
        confirmButton = {}
    )
}

fun colourSaver() = Saver<MutableState<Color>, String>(
    save = { state -> state.value.toHexString() },
    restore = { value -> mutableStateOf(value.toColor()) }
)

fun Color.toHexString(): String {
    return String.format(
        "#%02x%02x%02x%02x", (this.alpha * 255).toInt(),
        (this.red * 255).toInt(), (this.green * 255).toInt(), (this.blue * 255).toInt()
    )
}

fun String.toColor(): Color {
    return Color(android.graphics.Color.parseColor(this))
}