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
import androidx.compose.material.MaterialTheme
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
import com.example.jetpackcomposescaffoldlayout.Navigations
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.screens.ApartmentViewModel
import com.example.navigationexample.presentation.screens.BalanceViewModel
import com.example.navigationexample.presentation.screens.CalendarViewModel
import com.example.navigationexample.presentation.screens.ClientViewModel
import com.example.navigationexample.ui.theme.RentierTheme
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

//            val owner = LocalViewModelStoreOwner.current
//            owner?.let {
//                val viewModel: AppatmentViewModel = viewModel(
//                    it,
//                    "MainViewModel",
//                    AppatmentViewModelFactory(
//                        LocalContext.current.applicationContext
//                                as Application
//                    )
//                )
//            }

            RentierTheme {

                Surface(color = MaterialTheme.colors.background) {
                    var toolbarTitle by remember {
                        mutableStateOf("Home")
                    }
                    val scaffoldState =
                        rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
                    val scope = rememberCoroutineScope()

                    Scaffold(
                        modifier = Modifier.background(Color.White),
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

                        bottomBar = { BottomAppBar() },
                        snackbarHost = { state -> MySnackHost(state) },
                        isFloatingActionButtonDocked = true,
                        floatingActionButtonPosition = FabPosition.Center,
                        floatingActionButton = { FloatingActionButtonSample(scaffoldState) }
                    ){
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
        modifier = Modifier.background(Color(0xFF008800)),
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
                Icons.Filled.Settings,
                contentDescription = "Setting", modifier = Modifier.clickable {
                    Toast.makeText(context, "Open Setting", Toast.LENGTH_SHORT).show()
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
    DrawerContent(scaffoldState,scope)
}

@Composable
fun DrawerContent(scaffoldState: ScaffoldState, scope: CoroutineScope) {

    Column(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.baseline_home_work_24),
            modifier = Modifier
                .size(100.dp)
                .align(Alignment.CenterHorizontally), contentDescription = "Face"
        )

        Spacer(modifier = Modifier.padding(4.dp))
        Text(
            text = "Velmurugan",
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.align(
                Alignment.CenterHorizontally
            )
        )
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = "User1",
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

    fun getMenuList() : List<MenuItem> {
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
                    .background(Color.Black, RoundedCornerShape(8.dp)),
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
                            color = MaterialTheme.colors.primary,
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


