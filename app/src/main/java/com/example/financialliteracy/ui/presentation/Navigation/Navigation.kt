package com.example.financialliteracy.ui.presentation.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.financialliteracy.ui.presentation.CategoryDetail.CategoryDetail
import com.example.financialliteracy.ui.presentation.Home.Home
import com.example.financialliteracy.ui.presentation.Login.Login
import com.example.financialliteracy.ui.presentation.Profile.Profile
import com.example.financialliteracy.ui.presentation.Register.Register
import com.example.financialliteracy.ui.presentation.Splash.Splash
import com.example.financialliteracy.ui.presentation.StockDetail.StockDetailScreen
import com.example.financialliteracy.ui.presentation.StockList.StockListScreen

@Composable
fun Navigation(paddingValues: PaddingValues, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "home"){

        composable("splash"){
            Splash(navController= navController)
        }
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
        composable("categoryDetail/{id}",
            arguments = listOf(navArgument(
                "id"){
                type = NavType.StringType
            }))
        {
            val categoryId = it.arguments?.getString("id")
            CategoryDetail(navController = navController, categoryId)

        }
        composable("stockList"){
            StockListScreen(navController = navController)
        }
        composable("stockDetail/{symbol}",
            arguments = listOf(navArgument("symbol"){
                type = NavType.StringType
            }))
        {
            val stockSymbol = it.arguments?.getString("symbol")
            StockDetailScreen(navController = navController, stockSymbol)

        }

    }

}