package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.domain.usecase.validation.ValidationFormEvent
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientDitailsScreen(mainNavController: NavHostController, viewModel: AppatmentViewModel, clientPhone: String) {
    val allAppatment by viewModel.allApartments.observeAsState(listOf())

    Column(
        modifier = Modifier
            .background(Color(red = 41, green = 41, blue = 41))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp),
            shape = RoundedCornerShape(5.dp),
            elevation = 8.dp,
            onClick = {

//            navcontroller.navigate(route = "${Routs.mainScreenClients}?appatment_name=${appatmentItem.name}")
            }
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(3.dp),
                ) {
                Text(
                    clientPhone,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )
            }
        }



        Spacer(modifier = Modifier.padding(10.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.Center),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(modifier = Modifier.padding(end = 80.dp),
                onClick = {

                    mainNavController.navigate(Routs.mainScreenClients)
                })
            {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                    contentDescription = "Назад",

                    modifier = Modifier.size(55.dp),
                    tint = Color(223, 75, 0)
                )
            }
//            IconButton(
//                onClick = {
//                    viewModel.onFormEvent(ValidationFormEvent.onSubmit)
//                }
//            )
//            {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_check_24),
//                    contentDescription = "Добавить объект недвижимости",
//
//                    modifier = Modifier.size(55.dp),
//                    tint = Color(223, 75, 0)
//                )
//            }
        }
    }


//    IconButton(onClick = {
//        mainNavController.navigate(Routs.addAppatmentScreen)
//    }) {
//        Icon(
//            painter = painterResource(id = R.drawable.baseline_add_home_work_24),
//            contentDescription = "Добавить объект",
//
//            modifier = Modifier
//                .size(50.dp)
//                .padding(bottom = 3.dp),
//            tint = Color(223, 75, 0)
//
//        )
//    }
}