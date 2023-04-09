package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeex.ItemRow
import com.example.navigationexample.R
import com.example.navigationexample.domain.models.Item
import com.example.navigationexample.presentation.navigation.Routs


@Composable
fun MainScreen(navController: NavHostController) {
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
                    Item(
                        R.drawable.house1, "sdfsdfsdf", "asjfasda sda sda s da sd"
                    ),
                    Item(R.drawable.house1, "sdfsdfsdf", "asjfdgasjdgfasdgfasdf"),
                    Item(R.drawable.house1, "sdfsdfsdf", "asjfdgasjdgfasdgfasdf"),
                )
            ) { _, item ->
                ItemRow(item = item)
            }
        }

        Button(
            onClick = {navController.navigate(Routs.addAppatmentScreen)},
            shape = RoundedCornerShape(50.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
        ) {
            Text(text = "Добавить объект")
        }



//        Button(
//            onClick = {
//                navController.navigate(Routs.addAppatmentScreen)
//            }, modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .padding(6.dp)
//        ) {
//            Text("Добавить объект")
//        }





    }

}