package com.enesay.financialliteracy.ui.presentation.Trade

import android.widget.Toast
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
import java.util.Locale

@Composable
fun TradeScreen(navController: NavHostController, asset: Asset) {
    val tradeViewmodel : TradeViewmodel = hiltViewModel()
    val formattedPrice = String.format(Locale.US,"%,.2f", asset.price)

    Scaffold (Modifier.fillMaxSize()){ paddingValues->

        Column(modifier = Modifier.fillMaxSize().padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            // Varlık ismi ve fiyatı
            Text(asset.name, fontSize = 26.sp)
            Text(formattedPrice, fontSize = 22.sp)

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
//            val totalPrice = asset?.quote?.USD?.let { amount.toDoubleOrNull()?.times(it) } ?: 0.0
//            Text(text = "Total: $totalPrice USD", style = MaterialTheme.typography.labelLarge)

            Spacer(modifier = Modifier.height(16.dp))

            // Al ve Sat Butonları
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = {
                    tradeViewmodel.buyAsset(asset, amount.toDouble())
                    Toast.makeText(navController.context, "Alındı", Toast.LENGTH_SHORT).show()
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Green)) {
                    Text("Buy", fontWeight = FontWeight.Bold)
                }
                Button(onClick = {
                    tradeViewmodel.sellAsset(asset, amount.toDouble())
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.Red)) {
                    Text("Sell", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}
