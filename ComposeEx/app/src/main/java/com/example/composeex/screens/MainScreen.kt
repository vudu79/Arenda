package com.example.composeex.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composeex.ItemRow
import com.example.composeex.R
import com.example.composeex.models.Item


@Composable
@Preview
fun MainScreen() {
    Image(
        painter = painterResource(
            id = R.drawable.sky
        ),
        contentDescription = "im1",
        modifier = Modifier
            .fillMaxSize()
            .alpha(0.5f),
        contentScale = ContentScale.FillBounds
    )
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .padding(3.dp)

        ) {
            itemsIndexed(
                listOf(
                    Item(R.drawable.house1, "sdfsdfsdf", "asjfasda sda sda s da sd"
                    ),
                    Item(R.drawable.house1, "sdfsdfsdf", "asjfdgasjdgfasdgfasdf"),
                    Item(R.drawable.house1, "sdfsdfsdf", "asjfdgasjdgfasdgfasdf"),
                )
            ) { _, item ->
                ItemRow(item = item)
            }
        }
        Button(onClick = {}, modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(6.dp)) {
            Text("Добавить объект")
        }
    }

}