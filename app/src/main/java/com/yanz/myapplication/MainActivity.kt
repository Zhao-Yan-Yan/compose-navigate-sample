package com.yanz.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yanz.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Scaffold(
                        bottomBar = {
                            val currentBackStackEntry by navController.currentBackStackEntryAsState()
                            val destination = currentBackStackEntry?.destination
                            val route = destination?.route.orEmpty()
                            if (route == "home" || route == "me") {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(onClick = {
                                        navController.navigate("home") {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }

                                    }) {
                                        Text(text = "home")
                                    }
                                    Button(onClick = {
                                        navController.navigate("me") {
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    }) {
                                        Text(text = "me")
                                    }
                                }
                            }
                        }
                    ) {
                        it
                        MyAppHost(navController)
                    }
                }
            }
        }
    }

}

@Composable
fun MyAppHost(navController: NavHostController) {
    NavHost(navController = navController, "splash") {
        composable("splash") {
            SplashScreen(
                next = {
                    navController.navigateUp()
                    navController.navigate("home")
                }
            )
        }

        composable("home") {
            HomeScreen()
        }

        composable("me") {
            MeScreen()
        }
    }
}

@Composable
fun HomeScreen(
    viewmodel: HomeViewModel = viewModel()
) {
    var checked by rememberSaveable {
        mutableStateOf(true)
    }
    SideEffect {
        Log.e("TAG", "HomeScreen: SSSSS")
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "home")
        Checkbox(checked = checked, onCheckedChange = {
            checked = it
        })
    }
}

@Composable
fun MeScreen() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "me")
    }
}

@Composable
fun SplashScreen(
    next: () -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        delay(1500)
        next()
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = "splash")
    }
}

class HomeViewModel : ViewModel() {
    init {
        Log.e("TAG", "vm init: ")
    }
}