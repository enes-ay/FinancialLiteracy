package com.example.financialliteracy.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.financialliteracy.ui.presentation.CategoryDetail.CategoryDetail
import com.example.financialliteracy.ui.presentation.Home.Home
import com.example.financialliteracy.ui.presentation.Login.Login
import com.example.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.example.financialliteracy.ui.presentation.Register.Register

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
            val loginViewmodel : LoginViewmodel = hiltViewModel<LoginViewmodel>()
            Login(navController = navController, loginViewmodel)


        }
        composable("categoryDetail/{id}",
            arguments = listOf(navArgument("id"){
                type = NavType.StringType
            }))
        {
            val categoryId = it.arguments?.getString("id")
            CategoryDetail(navController = navController, categoryId)

        }
        composable("stockList"){
            com.example.financialliteracy.ui.presentation.StockList.List(navController = navController)
        }

    }

}