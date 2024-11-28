package com.enesay.financialliteracy.ui.presentation.Trade

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
import androidx.compose.runtime.getValue
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
fun TradeScreen(navController: NavHostController, asset: Asset) {
    val tradeViewmodel: TradeViewmodel = hiltViewModel()
    val formattedPrice = String.format(Locale.US, "%,.2f", asset.price)

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Asset Details
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
            }

            // Amount Input and Percentage Buttons
            var amount by remember { mutableStateOf("") }
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
                var selectedPercentage by remember { mutableStateOf<Int?>(null) }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(25, 50, 75, 100).forEach { percentage ->
                        Button(
                            onClick = {
                                selectedPercentage = percentage // Mark selected percentage
                                val maxAmount =
                                    1000.0 // Replace with user's actual balance or asset quantity
                                amount =
                                    String.format(Locale.US, "%.2f", maxAmount * percentage / 100)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedPercentage == percentage) primary_color else Color(
                                    0xFFE3F2FD
                                ), // Light Blue for unselected
                                contentColor = if (selectedPercentage == percentage) Color.White else Color.Black // Adjust text color
                            )
                        ) {
                            Text("$percentage%")
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Buy and Sell Buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        tradeViewmodel.buyAsset(asset, amount.toDoubleOrNull() ?: 0.0)
                        Toast.makeText(navController.context, "Buy", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = primary_color)
                ) {
                    Text("Buy", fontWeight = FontWeight.Bold, color = Color.White, fontSize = 20.sp)
                }

                Button(
                    onClick = {
                        tradeViewmodel.sellAsset(asset, amount.toDoubleOrNull() ?: 0.0)
                        Toast.makeText(navController.context, "Sell", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                ) {
                    Text(
                        "Sell",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}
