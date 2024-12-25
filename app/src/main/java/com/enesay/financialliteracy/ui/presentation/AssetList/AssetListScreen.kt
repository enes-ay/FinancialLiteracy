package com.enesay.financialliteracy.ui.presentation.AssetList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import com.enesay.financialliteracy.model.Stock.StockResponse
import com.enesay.financialliteracy.model.Stock.search.Result
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.theme.primary_color
import com.google.gson.Gson
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssetListScreen(navController: NavHostController) {
    val tabs = listOf("Crypto", "Favorites", "Stock", "Bonds")
    var selectedTabIndex by remember { mutableStateOf(0) }
    val assetListViewModel: AssetListViewmodel = hiltViewModel()
    val cryptoList by assetListViewModel.cryptoList.collectAsState()
    val stockList by assetListViewModel.stocks.collectAsState()

    val stockResponse by assetListViewModel.stockResponse.collectAsState()
    val errorMessage by assetListViewModel.errorMessage.collectAsState()

    LaunchedEffect(true) {
        assetListViewModel.getCryptoData()
    }

    Scaffold(
        topBar = {
            TopBarWithSearch(assetListViewModel = assetListViewModel)
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
                0 -> CryptosList(cryptoList, navController, assetListViewModel)
                1 -> FavoriteAssetsList()
                2 -> StocksList(
                    assetListViewModel = assetListViewModel,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun CryptosList(
    cryptoList: List<Asset>,
    navController: NavHostController,
    assetListViewModel: AssetListViewmodel
) {
    val isLoading by assetListViewModel.isLoading.collectAsState()  // Yükleme durumunu dinle
    val listState = rememberLazyListState()

    LazyColumn(state = listState, modifier = Modifier.padding(horizontal = 10.dp)) {
        items(cryptoList) { crypto ->
            AssetRow(crypto, onClick = {
                val crypto_json = Gson().toJson(crypto)
                navController.navigate("assetTrade/$crypto_json")
            })
        }

        // Yükleniyor göstergesi
        if (isLoading) {
            item {
                Box(
                    modifier = Modifier
                        .fillParentMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                if (lastVisibleItemIndex != null && lastVisibleItemIndex >= cryptoList.size - 1 && !isLoading) {
                    assetListViewModel.getCryptoData()
                }
            }
    }
}

@Composable
fun StocksList(assetListViewModel: AssetListViewmodel, navController: NavHostController) {
    val stockSearchList by assetListViewModel.stockSearchList.collectAsState()
    // Hisse senetlerinin listesi
    val tf = remember { mutableStateOf("") }

    LazyColumn(modifier = Modifier.padding(horizontal = 10.dp)) {
        stockSearchList.let {
            items(stockSearchList) { stock ->
                StockRow(stock, onClick = {
                    val stock_json = Gson().toJson(stock)
                    navController.navigate("assetTrade/$stock_json")
                })
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
            .background(MaterialTheme.colorScheme.background)
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
                    color = MaterialTheme.colorScheme.onPrimary,
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
fun AssetRow(asset: Asset, onClick: () -> Unit = {}, isShowQuantity: Boolean = false) {
    val formattedPrice = String.format(Locale.US, "%,.2f", asset.price)
    val formattedQuantity = String.format(Locale.US, "%,.3f", asset.quantity)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = asset.symbol,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = "$${formattedPrice}",
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
            if (isShowQuantity) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = formattedQuantity,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@Composable
fun StockRow(stock: Asset, onClick: () -> Unit = {}, isShowQuantity: Boolean = false) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSecondaryContainer),
        elevation = CardDefaults.elevatedCardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stock.symbol,
                    fontSize = 22.sp,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
            if (isShowQuantity) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "formattedQuantity",
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Light
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarWithSearch(modifier: Modifier = Modifier, assetListViewModel: AssetListViewmodel) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }

    Column {
        TopAppBar(
            title = {
                Text(
                    text = "Markets",
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = primary_color)
        )
        TextField(
            value = searchQuery,
            onValueChange = {
                searchQuery = it
                assetListViewModel.searchStockSymbol(searchQuery)
            },
            placeholder = {
                Text(
                    text = "Search asset",
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
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
                            contentDescription = "Clear Search",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            },
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = TextFieldDefaults.textFieldColors(
                containerColor = MaterialTheme.colorScheme.background,
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


