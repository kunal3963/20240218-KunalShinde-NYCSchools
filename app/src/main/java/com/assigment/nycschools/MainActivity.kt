package com.assigment.nycschools

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.assigment.nycschools.ui.theme.NYCSchoolsTheme
import com.assigment.nycschools.viewModels.NYCSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NYCSchoolsTheme {
                val nycViewModel = hiltViewModel<NYCSchoolViewModel>()
                AppMainScreen(nycViewModel, this)
            }
        }
    }
}

@Composable
fun AppMainScreen(nycViewModel: NYCSchoolViewModel, context: Context) {
    val navController = rememberNavController()
    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = drawerState.isOpen,
            drawerContent = {
                Drawer(
                    onDestinationClicked = { route ->
                        scope.launch {
                            drawerState.close()
                        }
                        navController.navigate(route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = DrawerScreens.AllSchools.route
            ) {
                composable(DrawerScreens.AllSchools.route) {
                    AllSchools(
                        openDrawer = {
                            openDrawer()
                        },
                        nycViewModel = nycViewModel,
                        context = context,
                        navController = navController
                    )
                }
                composable(DrawerScreens.DetailSchool.route) {
                    Details(
                        navController = navController,
                        nycViewModel = nycViewModel,
                        context = context,
                    )
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    NYCSchoolsTheme {
        Greeting("Android")
    }
}