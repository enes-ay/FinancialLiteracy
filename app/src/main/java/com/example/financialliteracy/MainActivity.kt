package com.example.financialliteracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.financialliteracy.ui.presentation.Home.BottomBar
import com.example.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.example.financialliteracy.ui.presentation.Navigation.Navigation
import com.example.financialliteracy.ui.presentation.Screen
import com.example.financialliteracy.ui.theme.FinancialLiteracyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val loginViewmodel : LoginViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            FinancialLiteracyTheme {

                Scaffold( modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val showBottomBar = shouldShowBottomBar(navController = navController)
                        if (showBottomBar) {
                            BottomBar(navController = navController)
                        }

                    }) { paddingValues ->

                    Navigation(navController = navController,paddingValues = paddingValues)

                }

            }
        }
    }
}

@Composable
private fun shouldShowBottomBar(navController: NavController): Boolean {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return when (currentDestination?.route) {
        Screen.Home.route, Screen.List.route, Screen.Profile.route, Screen.Portolio.route -> true // Replace with your routes
        else -> false
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinancialLiteracyTheme {
    }
}