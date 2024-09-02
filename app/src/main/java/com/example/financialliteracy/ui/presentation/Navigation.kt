package com.example.financialliteracy.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.Navigation
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Navigation(paddingValues: PaddingValues, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "login",){

        composable("home"){
            Home(navController= navController, paddingValues=paddingValues)
        }
        composable("profile"){
            Profile(navController = navController)
        }
        composable("list"){
            Profile(navController = navController)
        }
        composable("register"){
            Register(navController = navController)
        }
        composable("login"){
            Login(navController = navController)
        }
    }

}