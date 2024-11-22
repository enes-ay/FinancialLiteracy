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
import com.enesay.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.enesay.financialliteracy.ui.theme.primary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavHostController) {
    var viewModel: PortfolioViewmodel = hiltViewModel()
    var loginViewmodel: LoginViewmodel = hiltViewModel()
    var portfolioViewmodel: PortfolioViewmodel = hiltViewModel()
    val assets by viewModel.userAssets.collectAsState()
    val balance by viewModel.balance.collectAsState()
    val currentUserId by loginViewmodel.currentUser

    LaunchedEffect(balance){
        currentUserId?.let {
        viewModel.getBalance(it)
        portfolioViewmodel.getUserAssets(it)}
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
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
            BalanceCard(balance)

            if (assets.isEmpty()) {
                EmptyState(paddingValues)
            } else {
                AssetList(assets = assets)
            }
        }
    }
}

@Composable
fun BalanceCard(balance: Double) {
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
                fontSize = 24.sp,
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
                    fontSize = 25.sp
                )
                // Optional: Add an icon button for settings or actions
                    Icon(modifier = Modifier.size(46.dp),
                        imageVector =Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        tint = Color.White,
                        contentDescription = "Settings"
                    )

            }
        }
    }
}


@Composable
fun AssetList(assets: List<Asset>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 5.dp, horizontal = 16.dp)
    ) {
        items(assets) { asset ->
            AssetRow(asset)
        }
    }
}

@Composable
fun AssetRow(asset: Asset) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(8.dp), clip = false), // Gölge ve köşeleri yuvarlama
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp) // Hafif gri gölge efekti
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = asset.name, fontSize = 21.sp, fontWeight = FontWeight.Bold, color = primary_color)
            Text(text = asset.symbol,  fontWeight = FontWeight.Medium,style = MaterialTheme.typography.bodyLarge)
            Text(text = "${asset.quantity}",  fontWeight = FontWeight.Medium,style = MaterialTheme.typography.bodyLarge)
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
