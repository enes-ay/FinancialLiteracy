package com.enesay.financialliteracy.ui.presentation.Navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.enesay.financialliteracy.model.DataCrypto
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.model.Trade.toAsset
import com.enesay.financialliteracy.ui.presentation.CategoryDetail.CategoryDetail
import com.enesay.financialliteracy.ui.presentation.Home.Home
import com.enesay.financialliteracy.ui.presentation.Login.Login
import com.enesay.financialliteracy.ui.presentation.Portfolio.PortfolioScreen
import com.enesay.financialliteracy.ui.presentation.Profile.Profile
import com.enesay.financialliteracy.ui.presentation.Register.Register
import com.enesay.financialliteracy.ui.presentation.Splash.Splash
import com.enesay.financialliteracy.ui.presentation.StockDetail.StockDetailScreen
import com.enesay.financialliteracy.ui.presentation.AssetList.AssetListScreen
import com.enesay.financialliteracy.ui.presentation.Trade.TradeScreen
import com.google.gson.Gson

@Composable
fun Navigation(paddingValues: PaddingValues, navController: NavHostController) {

    NavHost(navController = navController, startDestination = "splash") {

        composable("splash") {
            Splash(navController = navController)
        }
        composable("home") {
            Home(navController = navController)
        }
        composable("profile") {
            Profile(navController = navController)
        }
        composable("list") {
            Profile(navController = navController)
        }
        composable("portfolio") {
            PortfolioScreen(navController = navController)
        }
        composable("register") {
            Register(navController = navController)
        }
        composable("login") {
            Login(navController = navController)

        }
        composable(
            "categoryDetail/{id}",
            arguments = listOf(navArgument(
                "id"
            ) {
                type = NavType.StringType
            })
        )
        {
            val categoryId = it.arguments?.getString("id")
            CategoryDetail(navController = navController, categoryId)

        }
        composable("stockList") {
            AssetListScreen(navController = navController)
        }
        composable("stockDetail/{symbol}",
            arguments = listOf(navArgument("symbol") {
                type = NavType.StringType
            })
        )
        {
            val stockSymbol = it.arguments?.getString("symbol")
            StockDetailScreen(navController = navController, stockSymbol)

        }
        composable("assetTrade/{crypto}",
            arguments = listOf(navArgument("crypto") {
                type = NavType.StringType
            })
        )
        {
            val crypto_json = it.arguments?.getString("crypto")
            val crypto = Gson().fromJson(crypto_json, Asset::class.java)
            TradeScreen(navController = navController, asset = crypto)
        }

    }

}