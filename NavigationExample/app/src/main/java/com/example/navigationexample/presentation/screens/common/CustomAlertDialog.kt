package com.example.navigationexample.presentation.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustomAlertDialog(onDismiss: () -> Unit, onOk: () -> Unit, message: String) {

    Dialog(onDismissRequest = { onDismiss() }, properties = DialogProperties(
        dismissOnBackPress = false,dismissOnClickOutside = false
    )
    ) {
        Card(
            //shape = MaterialTheme.shapes.medium,
            shape = RoundedCornerShape(10.dp),
            // modifier = modifier.size(280.dp, 240.dp)
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            elevation = 8.dp
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(Color.Red.copy(alpha = 0.8F)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,

                    ) {

//                    Image(
////                        painter = painterResource(),
//                        contentDescription = "Exit app",
//                        modifier = Modifier.fillMaxSize(),
//                        contentScale = ContentScale.FillWidth
//                    )
                }

                Text(
                    text =message ,
                    modifier = Modifier.padding(8.dp), fontSize = 20.sp
                )

//                Text(
//                    text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard",
//                    modifier = Modifier.padding(8.dp)
//                )

                Row(Modifier.padding(top = 10.dp)) {
                    OutlinedButton(
                        onClick = { onDismiss() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Отмена")
                    }


                    Button(
                        onClick = { onOk() },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .weight(1F)
                    ) {
                        Text(text = "Удалить")
                    }
                }

            }
        }
    }
}