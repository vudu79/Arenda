package com.example.navigationexample


import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
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
import androidx.compose.ui.unit.dp
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.BalanceViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel
import com.example.navigationexample.ui.theme.RentierTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModelApartment: ApartmentViewModel by viewModels()
    private val viewModelClient: ClientViewModel by viewModels()
    private val viewModelCalendar: CalendarViewModel by viewModels()
    private val viewModelBalance: BalanceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RentierTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MyScaffoldLayout(
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


@Composable
fun MyScaffoldLayout(
    viewModelApartment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelBalance: BalanceViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    LocalContext.current.applicationContext

    val drawerItemList = prepareNavigationDrawerItems()
    val selectedItem = remember { mutableStateOf(drawerItemList[0]) }
    val contextForToast = LocalContext.current.applicationContext
    val snackbarHostState = remember { SnackbarHostState() }
    var clickCount by remember {
        mutableStateOf(0) // or use mutableStateOf(0)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // add drawer content here
                // this is a column scope
                // so, if you add multiple elements, they are placed vertically
                Spacer(Modifier.height(12.dp))
                drawerItemList.forEach { item ->
                    NavigationDrawerItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(text = item.label) },
                        selected = (item == selectedItem.value),
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            selectedItem.value = item
                        },
                        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    ) {
        // app content
        // add scaffold here
        Scaffold(
            topBar = {
                MyTopAppBar {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                }
            },
            bottomBar = { MyBottomBar(contextForToast = contextForToast) },
            floatingActionButton = { MyFAB(contextForToast = contextForToast) },
            floatingActionButtonPosition = FabPosition.End,
            snackbarHost = { SnackbarHost(snackbarHostState) }
        ) { paddingValues ->
            // rest of the app's UI
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues = paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//                Text(text = "Rest of the app UI")
//                Button(
//                    onClick = {
//                        coroutineScope.launch {
//                            snackbarHostState.showSnackbar(
//                                message = "Snackbar # ${++clickCount}",
//                                duration = SnackbarDuration.Short
//                            )
//                        }
//                    }
//                ) {
//                    Text(text = "Show Snackbar")
//                }


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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onNavIconClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "SemicolonSpace") },
        navigationIcon = {
            IconButton(
                onClick = {
                    onNavIconClick()
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Navigation Items"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
        )
    )
}

private fun prepareNavigationDrawerItems(): List<NavigationDrawerData> {
    val drawerItemsList = arrayListOf<NavigationDrawerData>()

    // add items
    drawerItemsList.add(NavigationDrawerData(label = "Home", icon = Icons.Filled.Home))
    drawerItemsList.add(NavigationDrawerData(label = "Profile", icon = Icons.Filled.Person))
    drawerItemsList.add(NavigationDrawerData(label = "Cart", icon = Icons.Filled.ShoppingCart))
    drawerItemsList.add(NavigationDrawerData(label = "Settings", icon = Icons.Filled.Settings))

    return drawerItemsList
}

data class NavigationDrawerData(val label: String, val icon: ImageVector)


@Composable
fun MyBottomBar(contextForToast: Context) {
    val bottomBarItemsList = mutableListOf<BottomBarItem>()
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Default.Home, name = "Home"))
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Default.Person, name = "Profile"))
    bottomBarItemsList.add(BottomBarItem(icon = Icons.Default.Favorite, name = "Favorites"))

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        bottomBarItemsList.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.name) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    Toast.makeText(contextForToast, item.name, Toast.LENGTH_SHORT).show()
                }
            )
        }
    }
}

data class BottomBarItem(val icon: ImageVector, val name: String)

@Composable
fun MyFAB(contextForToast: Context) {
    FloatingActionButton(
        onClick = {
            Toast.makeText(contextForToast, "FAB", Toast.LENGTH_SHORT)
                .show()
        }
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "add icon")
    }
}


//@AndroidEntryPoint
//class MainActivity : ComponentActivity() {
//    private val viewModelApartment: ApartmentViewModel by viewModels()
//    private val viewModelClient: ClientViewModel by viewModels()
//    private val viewModelCalendar: CalendarViewModel by viewModels()
//    private val viewModelBalance: BalanceViewModel by viewModels()
//
//
//    NavHostView(
//    viewModelApartment = viewModelApartment,
//    viewModelClient = viewModelClient,
//    viewModelCalendar = viewModelCalendar,
//    viewModelBalance = viewModelBalance,
//    )
//
