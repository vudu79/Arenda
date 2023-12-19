@file:Suppress("UNUSED_EXPRESSION")

package com.example.navigationexample.presentation.screens.common

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.contentColorFor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.navigationexample.presentation.navigation.NavHostView
import com.example.navigationexample.presentation.navigation.batton_navigation.BottomNavItems
import com.example.navigationexample.presentation.viewmodels.ApartmentViewModel
import com.example.navigationexample.presentation.viewmodels.BalanceViewModel
import com.example.navigationexample.presentation.viewmodels.CalendarViewModel
import com.example.navigationexample.presentation.viewmodels.ClientViewModel
import kotlinx.coroutines.launch

data class ScaffoldSet(
    var isTopBarActive: Boolean = true,
    var isBottomBarActive: Boolean = true,
    var isFABActive: Boolean = false
)

@Composable
fun MyScaffoldLayout(
    viewModelApartment: ApartmentViewModel,
    viewModelClient: ClientViewModel,
    viewModelCalendar: CalendarViewModel,
    viewModelBalance: BalanceViewModel,
    scaffoldContent: @Composable () -> Unit
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
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.primaryContainer,
                drawerContentColor = MaterialTheme.colorScheme.primary
            ) {
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
                        colors = NavigationDrawerItemDefaults.colors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            unselectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                            unselectedTextColor = MaterialTheme.colorScheme.onPrimary
                        ),
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
            bottomBar = {
                MyBottomBar(contextForToast = contextForToast) },
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

                Divider(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary

                )

                scaffoldContent

                Divider(
                    modifier =
                    Modifier
                        .fillMaxWidth(),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary

                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(onNavIconClick: () -> Unit) {
    TopAppBar(

        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Моя недвижимость",
//                modifier = Modifier
//                    .padding(top = 15.dp)

            )
        },
//        modifier = Modifier
//            .bottomBorder(1.dp, color = MaterialTheme.colorScheme.primary)
//            .fillMaxHeight(0.1f),


        navigationIcon = {
            IconButton(
                modifier = Modifier
                    .padding(top = 5.dp),
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
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
            navigationIconContentColor = MaterialTheme.colorScheme.primary,

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

    val bottomBarItemsList = listOf(
        BottomNavItems.Clients,
        BottomNavItems.Calendar,
        BottomNavItems.Ballance,
        BottomNavItems.Appatments,
    )

    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar(
        modifier = Modifier
            .fillMaxHeight(0.13f)
        ,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.primary,
    ) {
        bottomBarItemsList.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(
                    imageVector = item.icon,
                    contentDescription = "Open Navigation Items"
                )
                },
                label = { Text(text = item.title,
//                    modifier = Modifier
//                        .padding(top = 10.dp)
                ) },
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    Toast.makeText(contextForToast, item.title, Toast.LENGTH_SHORT).show()
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    indicatorColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                ),
                modifier = Modifier
                    .fillMaxSize()
            )
        }
    }
}

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

