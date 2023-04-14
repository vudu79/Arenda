@file:OptIn(ExperimentalFoundationApi::class)

package com.example.composeex

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.screens.AppatmentViewModel
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog


@Composable
fun ClientRow(client: Client, navcontroller: NavController, viewModel: AppatmentViewModel) {
    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(128, 107, 90))
                .combinedClickable(
                    onClick = {
//                        navcontroller.navigate(Routs.mainScreenClients)
                    },
                    onLongClick = {
                        showCustomDialog = !showCustomDialog
                    }),
//                .border(3.dp, Color(223,75,0))

        )
        {
            Image(
                painter = painterResource(R.drawable.client1_round),
                contentDescription = "asd",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(3.dp)
                    .size(20.dp)
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(2.dp),


                ) {
                Text(
                    client.name,
                    modifier = Modifier.padding(2.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )

                Text(
                    client.phone,
                    modifier = Modifier.padding(2.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )

                Text(
                    text = client.appatment_name.toString(),
                    modifier = Modifier.padding(2.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )

            }

        }
    }

    if (showCustomDialog) {
        CustomAlertDialog(onDismiss = {
            showCustomDialog = !showCustomDialog
        }, onExit = {
            showCustomDialog = !showCustomDialog
            viewModel.deleteClient(client.name)
            viewModel.getAppatmentClients(client.appatment_name)
        })
    }

}