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

                        navcontroller.navigate(route = "${Routs.mainScreenClients}?appatment_name=${appatmentItem.name}")

                    },
                    onLongClick = {
                        showCustomDialog = !showCustomDialog
                    }),


            )
        {
            Image(
                painter = painterResource(R.drawable.house1),
                contentDescription = "asd",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(3.dp)
                    .size(64.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(10.dp),


                ) {
                Text(
                    appatmentItem.name,
                    modifier = Modifier.padding(3.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )
                Text(
                    appatmentItem.address,
                    modifier = Modifier.padding(3.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )
                Text(
                    appatmentItem.type,
                    modifier = Modifier.padding(3.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )
                Text(
                    appatmentItem.square.toString(),
                    modifier = Modifier.padding(3.dp),
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
        },
        message = "Объект недвижимости будет безвозвратно удален. Вы уверены?")
    }

}