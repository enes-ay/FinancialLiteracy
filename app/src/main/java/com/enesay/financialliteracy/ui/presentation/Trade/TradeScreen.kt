package com.enesay.financialliteracy.ui.presentation.Trade

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.theme.primary_color
import java.util.Locale

@Composable
fun TradeScreen(navController: NavHostController, asset: Asset,
                ) {
    // State for toggling between Buy and Sell
    val tradeViewmodel: TradeViewmodel = hiltViewModel()
    var isBuySelected by remember { mutableStateOf(true) }
    var amount by remember { mutableStateOf("") }
    var selectedPercentage by remember { mutableStateOf<Int?>(null) }
    val tradeState by tradeViewmodel.tradeState.observeAsState()

    val balance by tradeViewmodel.balance.observeAsState(0.0)
    val userAssets by tradeViewmodel.userAssets.collectAsState()

    val userAssetBalance = userAssets.find { it.id == asset.id }?.quantity ?: 0.0
    var warningMessage by remember { mutableStateOf("") }

    // Format price and balance values
    val formattedPrice = String.format(Locale.US, "%,.2f", asset.price)
    val formattedBalance = String.format(Locale.US, "%,.2f", balance)
    val formattedAssetBalance = String.format(Locale.US, "%,.2f", userAssetBalance)

    // Load user data when the composable is first displayed
    LaunchedEffect(Unit) {
        tradeViewmodel.loadUserData()
    }

    // Update warningMessage when tradeState changes
    LaunchedEffect(tradeState) {
        when (tradeState) {
            is TradeState.Warning -> {
                warningMessage = (tradeState as TradeState.Warning).message
            }
            else -> {
                warningMessage = "" // Clear warning message on success or other states
            }
        }
    }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Toggle Menu for Buy/Sell
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = { isBuySelected = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBuySelected) primary_color else Color(0xFFE3F2FD),
                        contentColor = if (isBuySelected) Color.White else Color.Black
                    )
                ) {
                    Text("Buy")
                }

                Button(
                    onClick = { isBuySelected = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isBuySelected) primary_color else Color(0xFFE3F2FD),
                        contentColor = if (!isBuySelected) Color.White else Color.Black
                    )
                ) {
                    Text("Sell")
                }
            }

            // Display Asset Details and Balance Information
            Column(horizontalAlignment = Alignment.Start, modifier = Modifier.padding(top = 10.dp)) {
                Text(
                    text = asset.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = primary_color
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$formattedPrice USD",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (isBuySelected) {
                        "Available Balance: $formattedBalance USDT"
                    } else {
                        "Available ${asset.name}: $formattedAssetBalance"
                    },
                    fontSize = 18.sp,
                    color = Color.Gray
                )
            }

            // Amount Input and Percentage Buttons
            Column {
                Text(
                    text = "Enter Amount",
                    fontSize = 21.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text("Amount") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Percentage Selection Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(25, 50, 75, 100).forEach { percentage ->
                        Button(
                            onClick = {
                                selectedPercentage = percentage
                                val maxAmount = if (isBuySelected) balance else userAssetBalance
                                amount =
                                    String.format(Locale.US, "%.2f", maxAmount * percentage / 100)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedPercentage == percentage) primary_color else Color(
                                    0xFFE3F2FD
                                ),
                                contentColor = if (selectedPercentage == percentage) Color.White else Color.Black
                            )
                        ) {
                            Text("$percentage%")
                        }
                    }
                }
            }

            // Display warning message
            if (warningMessage.isNotEmpty()) {
                Text(
                    text = warningMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Action Button (Buy or Sell)
            Button(
                onClick = {
                    val amountValue = amount.toDoubleOrNull() ?: 0.0
                    if (isBuySelected) {
                        tradeViewmodel.buyAsset(asset, asset.price, amountValue)
                    } else {
                        tradeViewmodel.sellAsset(asset, amountValue, asset.price)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBuySelected) primary_color else Color.Red
                )
            ) {
                Text(
                    if (isBuySelected) "Buy" else "Sell",
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}


