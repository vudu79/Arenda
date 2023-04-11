package com.example.composeex

import android.content.ClipData
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Appatment


@Composable
fun ItemRow(appatmentItem: Appatment) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(128, 107, 90))
//                .border(3.dp, Color(223,75,0))

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

}