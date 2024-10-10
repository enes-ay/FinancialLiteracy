package com.example.financialliteracy.ui.presentation.StockDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financialliteracy.ui.presentation.StockDetailViewmodel

@Composable
fun StockDetailScreen(navController: NavController, stockSymbol: String?) {

    val stockDetailViewmodel : StockDetailViewmodel = hiltViewModel()
    val singleStock = stockDetailViewmodel.singleStock.observeAsState()

    LaunchedEffect (true){
        if (!stockSymbol.isNullOrEmpty()){
            stockDetailViewmodel.getStock(stockSymbol)
        }
    }

    Scaffold { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "${singleStock.value?.c}")
        }

    }

}