package com.example.navigationexample.presentation.screens//package com.example.navigationexample.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.LiveData
import androidx.navigation.NavHostController
import com.example.navigationexample.R
import com.example.navigationexample.data.entity.Client
import com.example.navigationexample.presentation.navigation.Routs


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientDitailsScreen(
    mainNavController: NavHostController,
    viewModelClient: ClientViewModel,
    viewModelAppatment: AppatmentViewModel,
    clientPhone: String
) {
    val currentAppatment by viewModelAppatment.currentApartment.observeAsState()
    viewModelClient.getClient(clientPhone)
    val state = viewModelClient.uiClientState


    Column(
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(41, 41, 41))
    ) {

        Text(
            text = makeFullName(state),
            maxLines = 2,
            modifier = Modifier
                .background(Color(41, 41, 41))
                .align(Alignment.CenterHorizontally)
                .padding(15.dp),
            fontSize = 18.sp,
            color = Color.White
        )

        Spacer(modifier = Modifier.padding(10.dp))

        LazyColumn(
            modifier = Modifier
                .background(Color(red = 41, green = 41, blue = 41))
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            ) {

//        Статус _______________________________________________________________
            item {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Row() {
                        Text(
                            text = "Статус клиента",
                            maxLines = 1,
                            modifier = Modifier
                                .background(Color(41, 41, 41))
                                .padding(start = 5.dp),
                            fontSize = 12.sp,
                            color = Color(223, 75, 0)
                        )
                    }
                    Row() {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(3.dp)
                                .background(Color(red = 41, green = 41, blue = 41)),
                            shape = RoundedCornerShape(5.dp),
                            elevation = 8.dp,
                            onClick = {

                            }
                        ) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier
                                    .fillMaxWidth(0.70f)

                                    .background(Color(red = 41, 41, blue = 41)),
                            ) {

                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .background(Color(red = 41, green = 41, blue = 41))

                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.85f)
                                            .height(50.dp)
                                            .background(Color(red = 41, green = 41, blue = 41))

                                    ) {
                                        Text(
                                            text = state.value?.status ?: "нет данныъх",
                                            maxLines = 1,
                                            modifier = Modifier
                                                .align(Alignment.CenterStart)
                                                .background(Color(41, 41, 41))
                                                .padding(start = 5.dp),

                                            fontSize = 18.sp,
                                            color = Color(254, 253, 253, 255)
                                        )
                                    }
                                }

                            }
                        }
                    }
                }
            }




            item {
                Spacer(modifier = Modifier.padding(10.dp))

                Box(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentSize(Alignment.Center),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
//                            viewModelClient.getAppatmentClients(appatmentName)
                                mainNavController.navigate(route = "${Routs.mainScreenClients}/${currentAppatment?.name}")
                            })
                        {
                            Icon(
                                painter = painterResource(id = R.drawable.baseline_arrow_back_24),
                                contentDescription = "Назад",
                                modifier = Modifier.size(55.dp),
                                tint = Color(223, 75, 0)
                            )
                        }

                    }
                }
            }

        }
    }
}

fun makeFullName(state: LiveData<Client>): String {
    val fName = state.value?.firstName ?: ""
    val sName = state.value?.secondName ?: ""
    val lName = state.value?.lastName ?: ""
    return "$fName $sName $lName"


}

@Preview
@Composable
private fun ClientDitailsScreen() {
    ClientDitailsScreen()
}
