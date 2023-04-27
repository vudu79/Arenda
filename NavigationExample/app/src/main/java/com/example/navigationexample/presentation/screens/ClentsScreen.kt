package com.example.navigationexample.presentation.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.composeex.ClientItemRow
import com.example.navigationexample.presentation.navigation.Routs


@Composable
fun ClientsScreen(
    mainNavController: NavHostController,
    viewModel: AppatmentViewModel,
    appatmentName: String
) {
    val currentAppatment by viewModel.currentAppatment.observeAsState()
    val appatmentClients by viewModel.allAppatmentClients.observeAsState(listOf())
    val allClients by viewModel.allClients.observeAsState(listOf())

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
            text = currentAppatment?.name ?: "111",
            modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
            fontSize = 20.sp,
            color = Color(223, 75, 0)
        )
        LazyColumn(

            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.74f)

                .padding(3.dp)


        ) {
            items(appatmentClients) { item ->
                ClientItemRow(client = item, navcontroller = mainNavController, viewModel)
            }
        }

        Button(
            onClick = {
                mainNavController.navigate(
                    route = "${Routs.addClientScreen}?appatment_name=$appatmentName"

                )
            },
            shape = RoundedCornerShape(30.dp),
            modifier = Modifier
                .padding(top = 9.dp)


                .wrapContentHeight(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(223, 75, 0))
        ) {
            Text(text = "Добавить")
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