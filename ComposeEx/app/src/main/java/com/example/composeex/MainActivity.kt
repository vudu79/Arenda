package com.example.composeex

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.composeex.screens.MainScreen

class MainActivity : ComponentActivity() {
    private var addApatmantLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addApatmantLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                if (result.resultCode == RESULT_OK) {
                    Log.d("myTag", "kdsflskdlskdjfslkdfjslkdjf")
                }
            }
        intent = Intent(this, AddApatmentActivity::class.java)

        setContent {
            MainScreen(launcher = addApatmantLauncher, intent = intent)
        }


    }
}

//@SuppressLint("UnrememberedMutableState")
//@Composable
//private fun setItem(name: String, prof: String) {
//
//    var counter = remember {
//        mutableStateOf(0)
//    }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(10.dp)
//            .pointerInput(Unit) {
//                detectDragGesturesAfterLongPress { change, dragAmount ->
//                    Log.d("myTAG", "$dragAmount")
//                }
//            }
//            .clickable {
//                counter.value++
//            },
//        shape = RoundedCornerShape(15.dp),
//        elevation = 20.dp
//    ) {
//        Box(
//        ) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//
//                Image(
//                    painter = painterResource(id = R.drawable.diskordavatar1),
//                    contentDescription = "Аватар",
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier
//                        .padding(5.dp)
//                        .size(70.dp)
//                        .clip(CircleShape)
//                )
//                Column(
//                    modifier = Modifier.padding(start = 16.dp),
//
//                ) {
//                    Text(text = counter.value.toString(), Modifier.padding(bottom = 5.dp))
//                    Text(text = prof)
//                }
//
//            }
//
//        }
//    }
//}
//
//
//
