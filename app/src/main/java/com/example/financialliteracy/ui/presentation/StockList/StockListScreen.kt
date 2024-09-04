package com.example.financialliteracy.ui.presentation.StockList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun List(navController: NavController, modifier: Modifier = Modifier) {
    val stockList = remember { mutableStateListOf("") }

    LaunchedEffect(key1 = true) {
        stockList.add("GOOG")
        stockList.add("MICROSOFT")
        stockList.add("MICROSOFT")
        stockList.add("MICROSOFT")
        stockList.add("TSLA")
        stockList.add("AMZN")
        stockList.add("GOOG")
        stockList.add("META")
        stockList.add("TSLA")
        stockList.add("AMZN")
        stockList.add("GOOG")
        stockList.add("META")
        stockList.add("TSLA")
        stockList.add("AMZN")
        stockList.add("GOOG")
        stockList.add("META")
    }

    Scaffold { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                items(count = stockList.count()) {
                    val stock = stockList[it]
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }) {
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
                                Text(text = stock, textAlign = TextAlign.Start)

                            }
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .weight(1f),
                                horizontalAlignment = Alignment.End,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(text = "$234")

                            }
                        }
                    }
                }

            }
        }


    }
}