package com.enesay.financialliteracy.ui.presentation.Portfolio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.presentation.AssetList.AssetRow
import com.enesay.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.enesay.financialliteracy.ui.theme.primary_color
import com.google.gson.Gson
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavHostController) {
    var loginViewmodel: LoginViewmodel = hiltViewModel()
    var portfolioViewmodel: PortfolioViewmodel = hiltViewModel()
    val assets by portfolioViewmodel.userAssets.collectAsState()
    val balance by portfolioViewmodel.balance.collectAsState()
    val currentUserId by loginViewmodel.currentUser

    LaunchedEffect(balance){
        currentUserId?.let {
        portfolioViewmodel.calculateBalance(it)
        portfolioViewmodel.getUserAssets(it)}
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Portfolio", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary_color, titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            val formattedBalance = String.format(Locale.US,"%,.2f", balance)
            BalanceCard(formattedBalance)
            Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 18.dp)) {
                Text("Your Assets", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }

            if (assets.isEmpty()) {
                EmptyState(paddingValues)
            } else {
                AssetList(assets = assets, navController)
            }
        }
    }
}

@Composable
fun BalanceCard(balance: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(top = 10.dp)
            .padding(horizontal = 16.dp)
            .clickable { },
        colors = CardDefaults.cardColors(containerColor = primary_color)
        //  elevation = 4.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = "Total Balance",
                fontSize = 20.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = "$${balance}",
                    color = Color.White,
                    fontSize = 22.sp
                )
                // Optional: Add an icon button for settings or actions
                    Icon(modifier = Modifier.size(42.dp),
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = Color.White,
                        contentDescription = "Balance details"
                    )

            }
        }
    }
}


@Composable
fun AssetList(assets: List<Asset>, navController: NavHostController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 16.dp),
        contentPadding = PaddingValues(bottom = 75.dp)
    ) {
        items(assets) { asset ->
            AssetRow(asset, onClick = {
                val assetJson = Gson().toJson(asset)
                navController.navigate("assetTrade/$assetJson")
            })
        }
    }
}

@Composable
fun EmptyState(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = androidx.compose.ui.Alignment.Center
    ) {
        Text("No assets found.")
    }
}
