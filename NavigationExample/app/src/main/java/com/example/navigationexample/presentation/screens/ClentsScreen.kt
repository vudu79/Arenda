package com.example.navigationexample.presentation.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import androidx.navigation.NavHostController
//import com.example.composeex.ClientItemRow
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs
import com.example.navigationexample.presentation.screens.common.CustomAlertDialog
import com.kizitonwose.calendar.sample.compose.clickable
import java.time.LocalDate


@Composable
fun ClientsScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    viewModelCalendar: CalendarViewModel,
    appatmentName: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    val appatmentClients by viewModelClient.allApartmentClients.observeAsState(listOf())

    Column(
        modifier = Modifier
            .background(Color(red = 41, green = 41, blue = 41))
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Клиенты для ${currentAppatment?.name ?: "_"}",
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
                ClientItemRow(
                    client = item,
                    navcontroller = mainNavController,
                    viewModelClient = viewModelClient,
                    viewModelCalendar = viewModelCalendar
                )
            }
        }

        IconButton(onClick = {
            viewModelClient.resetState()
            mainNavController.navigate(route = "${Routs.addClientScreen}/$appatmentName")
        })
        {
            Icon(
                painter = painterResource(id = R.drawable.baseline_group_add_24),
                contentDescription = "Добавить клиента",
                modifier = Modifier.size(55.dp),
                tint = Color(223, 75, 0)
            )
        }
    }
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LazyItemScope.ClientItemRow(
    client: Client,
    navcontroller: NavController,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel
) {

    var showCustomDialog by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(75.dp)
            .padding(3.dp),
        shape = RoundedCornerShape(7.dp),
        elevation = 8.dp,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color(128, 107, 90))
                .combinedClickable(
                    onClick = {
                        navcontroller.navigate("${Routs.clientDitailsScreen}/${client.phone}")
                    },
                    onLongClick = {
                        showCustomDialog = !showCustomDialog
                    }),
            verticalAlignment = Alignment.CenterVertically
//                .border(3.dp, Color(223,75,0))

        )
        {

            Box(
                modifier = Modifier

                    .fillMaxHeight()
                    .padding(10.dp)
                    .clickable {
                        makeCall(context, client.phone)
                    },

                ) {
                Image(
                    painter = painterResource(R.drawable.baseline_phone_24),
                    contentDescription = "asd",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(top = 2.dp)
                        .size(50.dp)
                        .clip(CircleShape)
                )
            }

            Divider(
                color = Color(client.clientColor).copy(alpha = 0.8f),
                modifier = Modifier
                    .fillMaxHeight(0.8f)  //fill the max height
                    .width(3.dp)
            )

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxWidth(0.70f)
                    .padding(start = 10.dp),


                ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            text = client.firstName,
                            modifier = Modifier.padding(1.dp),
                            fontSize = 20.sp,
                            textAlign = TextAlign.Justify,
                            color = Color(red = 41, green = 41, blue = 41),
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = client.phone,
                            modifier = Modifier.padding(2.dp),
                            maxLines = 1,
                            color = Color(red = 41, green = 41, blue = 41),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )


                        Text(
                            text = "${LocalDate.ofEpochDay(client.inDate ?: 0)} - ${
                                LocalDate.ofEpochDay(
                                    client.inDate ?: 0
                                )
                            }",
                            modifier = Modifier.padding(2.dp),
                            maxLines = 1,
                            color = Color(red = 41, green = 41, blue = 41),
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

    if (showCustomDialog) {
        CustomAlertDialog(onDismiss = {
            showCustomDialog = !showCustomDialog
        }, onOk = {
            showCustomDialog = !showCustomDialog

            viewModelClient.deleteClient(client.firstName)
            viewModelClient.getAppatmentClients(client.appatmentName)
            viewModelCalendar.updateDaysMapForCalendar(client.appatmentName)
        },
            message = "Клиент будет безвозвратно удален. Вы уверены?"
        )
    }

}

fun makeCall(context: Context, number: String) {
    val intent = Intent(Intent.ACTION_DIAL);
    intent.data = Uri.parse("tel:$number")
    startActivity(context, intent, null)
}


