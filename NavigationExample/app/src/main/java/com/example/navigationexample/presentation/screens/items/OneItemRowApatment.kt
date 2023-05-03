package com.example.composeex

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Appatment
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.AppatmentViewModel
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog


@OptIn(ExperimentalMaterialApi::class, ExperimentalFoundationApi::class)
@Composable
fun AppatmentItemRow(
    appatmentItem: Appatment,
    navcontroller: NavController,
    viewModel: AppatmentViewModel
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

    iconHomeType =  when(appatmentItem.type){
        "Квартира" -> R.drawable.baseline_cottage_24
        "Аппартаменты" ->  R.drawable.baseline_apartment_24
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
                        viewModel.getAppatmentClients(appatmentItem.name)
                        viewModel.setCurrentAppatment(appatmentItem)
                        viewModel.updateApartmentPlanedDays(appatmentItem.name)
                        viewModel.updateDaysMapForCalendar(appatmentItem.name)
                        navcontroller.navigate(route = "${Routs.mainScreenClients}?appatment_name=${appatmentItem.name}")
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
                    appatmentItem.name,
                    modifier = Modifier.padding(1.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )
                Text(
                    appatmentItem.address,
                    modifier = Modifier.padding(1.dp),
                    maxLines = 2,
                    fontSize = 10.sp
                )
                Text(
                    appatmentItem.type,
                    modifier = Modifier.padding(1.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )
                Text(
                    appatmentItem.rentalPeriod,
                    modifier = Modifier.padding(bottom = 5.dp, start = 1.dp, end = 1.dp, top = 1.dp ),
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
            viewModel.deleteAppatment(appatmentItem.name)
            viewModel.updateApartmentPlanedDays(appatmentItem.name)
        },
        message = "Объект недвижимости будет безвозвратно удален. Вы уверены?")
    }

}