package com.example.financialliteracy.ui.presentation.Portfolio

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioScreen(navController: NavHostController) {
    var viewModel: PortfolioViewmodel = hiltViewModel()
    val assets by viewModel.assets.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("My Portfolio") })
        }
    ) { paddingValues ->

        if (assets.isEmpty()) {
            EmptyState(paddingValues)
        } else {
            AssetList(assets = assets, paddingValues)
        }
    }
}

@Composable
fun AssetList(assets: List<Asset>, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
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
            .padding(vertical = 4.dp),
        //elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = asset.name, style = MaterialTheme.typography.labelMedium)
            Text(text = "Value: $${asset.value}", style = MaterialTheme.typography.bodyMedium)
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

@Preview(showBackground = true)
@Composable
fun AssetListPreview() {
    val mockAssets = listOf(
        Asset(1, "Gold", 1500.0),
        Asset(2, "Bitcoin", 20000.0),
        Asset(3, "TSLA", 1000.0)
    )
    AssetList(assets = mockAssets, paddingValues = PaddingValues(12.dp))
}