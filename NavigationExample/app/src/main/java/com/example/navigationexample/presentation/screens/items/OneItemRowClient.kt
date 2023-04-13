package com.example.composeex

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientRow(client: Client, navcontroller: NavController) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp,
        onClick = {
            navcontroller.navigate(Routs.mainScreenClients)
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(128, 107, 90))
//                .border(3.dp, Color(223,75,0))

        )
        {
            Image(
                painter = painterResource(R.drawable.cat),
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
                    client.name,
                    modifier = Modifier.padding(3.dp),
                    fontSize = 25.sp,
                    textAlign = TextAlign.Justify,
                    color = Color(0, 0, 0)
                )

                Text(
                    client.phone,
                    modifier = Modifier.padding(3.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )

                Text(
                    text = client.appatment_name.toString(),
                    modifier = Modifier.padding(3.dp),
                    maxLines = 1,
                    fontSize = 10.sp
                )

            }

        }
    }

}