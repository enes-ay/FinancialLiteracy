package com.example.financialliteracy.ui.presentation.Trade

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.financialliteracy.ui.presentation.Portfolio.Asset

@Composable
fun TradeScreen(  navController: NavHostController, assetSymbol: String?, assetPrice: Int?) {
    Scaffold (Modifier.fillMaxSize()){ paddingValues->

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            // Varlık ismi ve fiyatı
            Text(text = "${assetSymbol} (${assetSymbol})", style = MaterialTheme.typography.displayMedium)
            Text(text = "${assetPrice} USD", style = MaterialTheme.typography.displaySmall)

            Spacer(modifier = Modifier.height(16.dp))

            // İşlem miktarı ve toplam tutar
            var amount by remember { mutableStateOf("") }
            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Toplam işlem tutarı
            val totalPrice = assetPrice?.let { amount.toDoubleOrNull()?.times(it) } ?: 0.0
            Text(text = "Total: $totalPrice USD", style = MaterialTheme.typography.labelLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Al ve Sat Butonları
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {  }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                    Text("Buy", fontWeight = FontWeight.Bold)
                }
                Button(onClick = {  }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Sell", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TradeScreenPreview() {
    val mockAssets = listOf(
        Asset(1, "Gold", 1500.0, symbol = "XAU"),
        Asset(2, "Bitcoin", 20000.0),
        Asset(3, "TSLA", 1000.0)
    )
    //TradeScreen(navController = rememberNavController(),asset = mockAssets.get(0))
}