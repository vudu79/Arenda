package com.example.navigationexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.SnackbarResult
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.MaterialTheme
import com.example.jetpackcomposescaffoldlayout.Navigations
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.utils.bottomBorder
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.BalanceViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel
import com.example.navigationexample.ui.theme.RentieTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModelApartment: ApartmentViewModel by viewModels()
    private val viewModelClient: ClientViewModel by viewModels()
    private val viewModelCalendar: CalendarViewModel by viewModels()
    private val viewModelBalance: BalanceViewModel by viewModels()


    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            RentieTheme {

                Surface(color = MaterialTheme.colorScheme.primaryContainer) {
                    val toolbarTitle by remember {
                        mutableStateOf("Моя недвижимость")
                    }
                    val scaffoldState =
                        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
                    val scope = rememberCoroutineScope()

                    Scaffold(
                        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer),
                        scaffoldState = scaffoldState,
                        topBar = {
                            AppToolbar(
                                scaffoldState = scaffoldState,
                                scope = scope,
                                toolbarTitle = toolbarTitle
                            )
                        }, drawerContent = {
                            DrawerContent(scaffoldState = scaffoldState, scope = scope)
                        },

//                        bottomBar = { BottomAppBar() },
                        snackbarHost = { state -> MySnackHost(state) },
                        isFloatingActionButtonDocked = false,
                        floatingActionButtonPosition = FabPosition.End,
                        floatingActionButton = { FloatingActionButtonSample(scaffoldState) }
                    ) {
                        NavHostView(
                            viewModelApartment = viewModelApartment,
                            viewModelClient = viewModelClient,
                            viewModelCalendar = viewModelCalendar,
                            viewModelBalance = viewModelBalance,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TopAppBarSample() {
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    AppToolbar(scaffoldState = scaffoldState, scope = scope, toolbarTitle = "Home")
}




@Composable
fun AppToolbar(scaffoldState: ScaffoldState, scope: CoroutineScope, toolbarTitle: String) {
    TopAppBar(
        title = { Text(text = toolbarTitle) },
        modifier = Modifier.background(MaterialTheme.colorScheme.primaryContainer)
            .bottomBorder(1.dp, MaterialTheme.colorScheme.primary),
        navigationIcon = {
            Icon(
                Icons.Filled.Menu,
                contentDescription = "Menu", modifier = Modifier.clickable {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )
        },
        actions = {
            val context = LocalContext.current
            Icon(
                Icons.Filled.Search,
                contentDescription = "Search", modifier = Modifier.clickable {
                    Toast.makeText(context, "Searching", Toast.LENGTH_SHORT).show()
                }
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun DrawerContentSample() {
    val scaffoldState =
        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    DrawerContent(scaffoldState, scope)
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(1.dp, MaterialTheme.colorScheme.primary)
    ) {

        Image(
            painter = painterResource(id = R.drawable.baseline_home_work_24),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally), contentDescription = "Face"
        )

        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Валюта",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Напоминания",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Резервная копия",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Рассылка",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Настройки",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "Связаться с разработчиком",
            Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(8.dp, 4.dp)
                .clickable {
                    scope.launch {
                        scaffoldState.drawerState.close()
                    }
                })
        Spacer(modifier = Modifier.padding(4.dp))
    }
}


@Preview(showBackground = true)
@Composable
fun BottomAppBar() {
    data class MenuItem(val title: String, val icon: ImageVector)

    fun getMenuList(): List<MenuItem> {
        val menuItems = mutableListOf<MenuItem>()
        menuItems.add(MenuItem(Navigations.HOME.name, Icons.Filled.Home))
        menuItems.add(MenuItem(Navigations.SEARCH.name, Icons.Filled.Search))
        menuItems.add(MenuItem(Navigations.FAVORITE.name, Icons.Filled.Favorite))
        menuItems.add(MenuItem(Navigations.SETTINGS.name, Icons.Filled.Settings))
        return menuItems
    }

    val bottomMenuList = getMenuList()

    BottomNavigation {
        bottomMenuList.forEach { bottomMenu ->
            BottomNavigationItem(
                selected = bottomMenu.title == "Home",
                onClick = {

                },
                icon = {
                    Icon(
                        imageVector = bottomMenu.icon,
                        contentDescription = bottomMenu.title
                    )
                })
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MySnackHost(state: SnackbarHostState) {
    SnackbarHost(
        state,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier
                    .padding(8.dp)
                    .background(Color.Gray, RoundedCornerShape(8.dp)),
                action = {
                    Text(
                        text = "HIDE",
                        modifier = Modifier
                            .padding(8.dp)
                            .clickable {
                                state.currentSnackbarData?.dismiss()
                            },
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 18.sp
                        )
                    )
                }
            ) {
                Text(text = data.message)
            }
        })
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FloatingActionButtonSample(scaffoldState: ScaffoldState) {
    val scope = rememberCoroutineScope()
    FloatingActionButton(onClick = {
        scope.launch {
            when (scaffoldState.snackbarHostState.showSnackbar(
                message = "Jetpack Compose Snackbar",
                actionLabel = "Ok"
            )) {
                SnackbarResult.Dismissed ->
                    Log.d("TAB", "Dismissed")

                SnackbarResult.ActionPerformed ->
                    Log.d("TAB", "Action!")
            }
        }
    }) {
        Text("X")
    }
}


