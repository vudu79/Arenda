package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composeex.AppatmentItemRow
import com.example.navigationexample.R
import com.example.navigationexample.presentation.navigation.Routs


@Composable
fun MainScreen(mainNavController: NavHostController, viewModel: AppatmentViewModel) {

    val allAppatment by viewModel.allApartments.observeAsState(listOf())

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
            .background(Color(red = 41, green = 41, blue = 41))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            "Арендуемые объекты",
            modifier = Modifier.padding(10.dp),
            fontSize = 22.sp,
            color = Color(223, 75, 0).copy(alpha = 0.5f),
            fontWeight = FontWeight.Bold,
        )
        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.87f)
                .padding(3.dp)

        ) {
            items(allAppatment) { item ->
//                Log.d("myTag", allAppatment.toString())
                AppatmentItemRow(
                    appatmentItem = item,
                    navcontroller = mainNavController,
                    viewModel
                )
            }
        }


        IconButton(onClick = {
            mainNavController.navigate(Routs.addAppatmentScreen)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_home_work_24),
                contentDescription = "Добавить объект",

                modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 3.dp),
                tint = Color(223, 75, 0)

            )

        }


//        Button(
//            onClick = {
//                mainNavController.navigate(Routs.addAppatmentScreen)
//            },
//            shape = RoundedCornerShape(30.dp),
//            modifier = Modifier
//                .fillMaxWidth(0.6f)
//                .fillMaxHeight(),
//            colors = ButtonDefaults.buttonColors(backgroundColor = Color(223, 75, 0))
//        ) {
//            Text(text = "Добавить объект")
//        }


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