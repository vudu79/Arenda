package com.example.navigationexample.presentation.screens

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Apartment
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.utils.CustomAlertDialog
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel


@Composable
fun AppartmentsScreen(
    mainNavController: NavHostController,
    viewModelAppatment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel
) {
    val allApartments by viewModelAppatment.allApartments.observeAsState(listOf())

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
            items(allApartments) { item ->
//                // Log.d("myTag", allAppatment.toString())
                AppatmentItemRow(
                    apartmentItem = item,
                    navcontroller = mainNavController,
                    viewModelAppatment = viewModelAppatment,
                    viewModelCalendar = viewModelCalendar,
                    viewModelClient = viewModelClient
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


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppatmentItemRow(
    apartmentItem: Apartment,
    navcontroller: NavController,
    viewModelAppatment: ApartmentViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelClient: ClientViewModel
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    var iconHomeType by remember {
        mutableStateOf<Int>(0)
    }

    iconHomeType = when (apartmentItem.type) {
        "Квартира" -> R.drawable.baseline_cottage_24
        "Аппартаменты" -> R.drawable.baseline_cottage_24
        "Комерческое" -> R.drawable.baseline_home_work_24
        else -> R.drawable.baseline_cottage_24
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        onClick = {

//            navcontroller.navigate(route = "${Routs.mainScreenClients}?appatment_name=${appatmentItem.name}")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(128, 107, 90))
                .combinedClickable(
                    onClick = {
                        Log .d("myTag","апартаменты - ${apartmentItem.name}" )
                        viewModelClient.getApartmentClients(apartmentItem.name)
                        viewModelAppatment.setCurrentApartment(apartmentItem)
                        viewModelCalendar.updateApartmentPlanedDays(apartmentItem.name)
                        viewModelCalendar.updateDaysMapForCalendar(apartmentItem.name)
                        navcontroller.navigate(route = "${Routs.apartmentFinanceScreen}/${apartmentItem.name}")
                    },
                    onLongClick = {
                        showCustomDialog = !showCustomDialog
                    }),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Image(
                painter = painterResource(iconHomeType),
                contentDescription = "asd",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(start = 10.dp, end = 5.dp)
                    .size(50.dp)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(3.dp),


                ) {
                Text(
                    apartmentItem.name,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )
                apartmentItem.address?.let {
                    Text(
                        it,
                        modifier = Modifier.padding(1.dp),
                        maxLines = 2,
                        fontSize = 10.sp
                    )
                }
                Text(
                    apartmentItem.type,
                    modifier = Modifier.padding(1.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )
                Text(
                    apartmentItem.rentalPeriod,
                    modifier = Modifier.padding(
                        bottom = 5.dp,
                        start = 1.dp,
                        end = 1.dp,
                        top = 1.dp
                    ),
                    maxLines = 1,
                    fontSize = 10.sp
                )
            }


            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier.padding(top = 10.dp, end = 5.dp, bottom = 5.dp)
            ) {
                Text("500000", modifier = Modifier.padding(3.dp), Color.Green, fontSize = 15.sp)
                Text("1000", modifier = Modifier.padding(3.dp), Color.Red, fontSize = 15.sp)
            }

        }
    }

    if (showCustomDialog) {
        CustomAlertDialog(onDismiss = {
            showCustomDialog = !showCustomDialog
        }, onOk = {
            showCustomDialog = !showCustomDialog
            viewModelAppatment.deleteApartment(apartmentItem.name)
            viewModelCalendar.updateApartmentPlanedDays(apartmentItem.name)
        },
            message = "Объект недвижимости будет безвозвратно удален. Вы уверены?"
        )
    }

}