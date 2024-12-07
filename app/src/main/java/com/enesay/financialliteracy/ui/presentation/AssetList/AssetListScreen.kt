package com.enesay.financialliteracy.ui.presentation.AssetList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.enesay.financialliteracy.model.DataCrypto
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.presentation.Trade.TradeViewmodel
import com.enesay.financialliteracy.ui.theme.primary_color
import com.google.gson.Gson
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(navController: NavHostController) {
    val tabs = listOf("Crypto", "Favorites", "Stock","Bonds")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val assetListViewModel : AssetListViewmodel = hiltViewModel()
    val tradeViewmodel : TradeViewmodel = hiltViewModel()
    val cryptoList by assetListViewModel.cryptoData.collectAsState()

    LaunchedEffect(true) {
        assetListViewModel.getCryptoData()
    }

    Scaffold(
        topBar = {
            TopBarWithSearch()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            // Modern Tab Row
            ModernTabRow(
                tabs = tabs,
                selectedTabIndex = selectedTabIndex,
                onTabSelected = { selectedTabIndex = it }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Display content based on the selected tab
            when (selectedTabIndex) {
                0 -> CryptosList(cryptoList, navController, tradeViewmodel)
                1 -> FavoriteAssetsList()
                2 -> StocksList()
            }
        }
    }
}

@Composable
fun ModernTabRow(
    tabs: List<String>,
    selectedTabIndex: Int,
    onTabSelected: (Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(vertical = 8.dp)
    ) {
        items(tabs.size) { index ->
            val isSelected = selectedTabIndex == index
            Box(
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        if (isSelected) primary_color else Color.Transparent
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onTabSelected(index) }
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                Text(
                    text = tabs[index],
                    color = if (isSelected) Color.White else Color.Black,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
            }
        }
    }
}


@Composable
fun FavoriteAssetsList() {
    // Favori varlıkların listesi
    LazyColumn(
        modifier = Modifier.padding(horizontal = 10.dp)
    ) {
        items(5) { index ->
//            val asset = Asset(
//                1,
//                "Bitcoin",
//                23532.235,
//                1242,
//                "BTC"
//            )
//            AssetRow(asset = asset)
        }
    }
}

@Composable
fun CryptosList(cryptoList: List<Asset>, navController: NavHostController, tradeViewmodel: TradeViewmodel) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {
        items(cryptoList) { crypto ->
            AssetRow(crypto, onClick = {
                val crypto_json = Gson().toJson(crypto)
                navController.navigate("assetTrade/$crypto_json")
            })
        }
    }
}

@Composable
fun AssetRow(asset: Asset, onClick: () -> Unit = {}) {
    val formattedPrice = String.format(Locale.US,"%,.2f", asset.price)
    val formattedQuantity = String.format(Locale.US,"%,.2f", asset.quantity)
   // val formattedMarketCap = String.format(Locale.US,"%,.2f", crypto.)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp),
        ) {
            Text(text = "${asset.symbol}", fontSize = 23.sp, color = primary_color, fontWeight = FontWeight.Medium)
            Text(text = "$${formattedPrice}", fontSize = 18.sp, fontWeight = FontWeight.Medium)
            Text(text = "${formattedQuantity}", fontSize = 17.sp, fontWeight = FontWeight.Light)
        }
    }
}

@Composable
fun StocksList() {
    // Hisse senetlerinin listesi
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp)
    ) {


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(modifier: Modifier = Modifier) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = { Text(text = "Markets", color = Color.White, fontWeight = FontWeight.Medium) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = primary_color)
        )
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = "Search asset") },
            leadingIcon = {
                if (isFocused) {
                    IconButton(onClick = {
                        searchQuery = ""
                        focusManager.clearFocus() // Focusu temizleyerek aramadan çık
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack, // Geri oku göster
                            contentDescription = "Back Icon"
                        )
                    }
                } else {
                    Icon(
                        imageVector = Icons.Default.Search, // Arama ikonunu göster
                        contentDescription = "Search Icon"
                    )
                }
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                    }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Clear Search"
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused // Focus durumunu takip et
                }
        )

    }
}


