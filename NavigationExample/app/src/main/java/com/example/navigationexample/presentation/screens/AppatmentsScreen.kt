package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.composeex.ItemRow
import com.example.navigationexample.R
import com.example.navigationexample.presentation.navigation.Routs


@Composable
fun MainScreen(navController: NavHostController, viewModel: AppatmentViewModel) {

    val allAppatment by viewModel.allAppatments.observeAsState(listOf())

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
    Column(
        modifier = Modifier
            .background(Color(R.color.purple_500))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.90f)
                .padding(3.dp)

        ) {
            items(allAppatment) { item ->
                Log.d("myTag", allAppatment.toString())
                ItemRow(appatmentItem = item)

            }
        }

        Button(
            onClick = { navController.navigate(Routs.addAppatmentScreen) },
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