package com.example.financialliteracy.ui.presentation.StockList

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListScreen(navController: NavController) {
    val stockListViewmodel: StockListViewmodel = hiltViewModel()

    // Arama alanı için bir state ekleyelim
    var query by remember { mutableStateOf("") }

    val stockList by stockListViewmodel.stockList.observeAsState()
    val searchList by stockListViewmodel.searchList.observeAsState()

    LaunchedEffect(key1 = true) {
        stockListViewmodel.getStocks("TSLA")
    }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Arama alanı burada
            TextField(
                value = query,
                onValueChange = { newQuery ->
                    query = newQuery
                    if (newQuery.isNotEmpty()) {
                        stockListViewmodel.searchStock(newQuery) // Arama yapılıyor
                    }
                },
                label = { Text("Seaamrch Stock Symbol") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                if (searchList!= null){
                    items (searchList!!.count()) {
                        val stock = searchList!![it]
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(70.dp)
                                    .padding(horizontal = 30.dp),
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {

                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    horizontalAlignment = Alignment.Start,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    if (stock != null) {
                                        Text(
                                            text = "${stock.symbol}",
                                            textAlign = TextAlign.Start
                                        )

                                    }

                                }
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .weight(1f),
                                    horizontalAlignment = Alignment.End,
                                    verticalArrangement = Arrangement.Center
                                ) {

                                }
                            }
                        }
                    }
                }

            }

        }
    }
}
