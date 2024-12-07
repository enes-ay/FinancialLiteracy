package com.enesay.financialliteracy.ui.presentation.Trade

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.theme.primary_color
import kotlinx.coroutines.delay
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TradeScreen(
    navController: NavHostController, asset: Asset,
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

    if (tradeState is TradeState.Success || tradeState is TradeState.Warning || tradeState is TradeState.Error) {
        val (message, icon, iconColor) = when (tradeState) {
            is TradeState.Success -> Triple(
                if (isBuySelected) "Purchase Successful!" else "Sale Successful!",
                Icons.Default.CheckCircle,
                Color.Green
            )

            is TradeState.Warning -> Triple(
                (tradeState as TradeState.Warning).message,
                Icons.Default.Warning,
                Color.Yellow
            )

            is TradeState.Error -> Triple(
                (tradeState as TradeState.Error).message,
                Icons.Default.Warning,
                Color.Red
            )

            else -> null
        } ?: return

        TradeResultDialog(
            message = message,
            icon = icon,
            iconColor = iconColor,
            onDismiss = { tradeViewmodel.resetTradeState() }
        )

        // Dialog'u otomatik olarak kapat
        LaunchedEffect(tradeState) {
            delay(1300) // 1 saniye bekle
            tradeViewmodel.resetTradeState()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Trade", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary_color, titleContentColor = Color.White
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(12.dp),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            // Display Asset Details and Balance Information
            Column(horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.Top) {
                Text(
                    text = asset.name,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = primary_color
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$$formattedPrice",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(16.dp))

            }
            // Toggle Menu for Buy/Sell
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Button(
                    onClick = { isBuySelected = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isBuySelected) primary_color else Color(0xFFE3F2FD),
                        contentColor = if (isBuySelected) Color.White else Color.Black
                    )
                ) {
                    Text(stringResource(R.string.btn_buy))
                }

                Button(
                    onClick = { isBuySelected = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (!isBuySelected) Color.Red else Color(0xFFE3F2FD),
                        contentColor = if (!isBuySelected) Color.White else Color.Black
                    )
                ) {
                    Text(stringResource(R.string.btn_sell))
                }
            }

            // Amount Input and Percentage Buttons
            Column(modifier = Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    label = { Text(stringResource(R.string.txt_amount)) },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    modifier = Modifier.padding(horizontal = 5.dp),
                    text = if (isBuySelected) {
                        "Max: $formattedBalance USD"
                    } else {
                        "Max: $formattedAssetBalance ${asset.symbol}"
                    },
                    fontSize = 15.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Percentage Selection Button
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    listOf(25, 50, 75, 100).forEach { percentage ->
                        Button(
                            onClick = {
                                selectedPercentage = percentage
                                val maxAmount = if (isBuySelected) balance else userAssetBalance
                                amount = if (isBuySelected) {
                                    String.format(
                                        Locale.US,
                                        "%.2f",
                                        (maxAmount * percentage / 100) / asset.price
                                    )
                                } else {
                                    String.format(Locale.US, "%.2f", (maxAmount * percentage / 100))
                                }
                            },
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(horizontal = 4.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedPercentage == percentage && isBuySelected) {
                                    primary_color
                                } else if (selectedPercentage == percentage && !isBuySelected) {
                                    Color.Red
                                } else {
                                    Color(
                                        0xFFE3F2FD
                                    )
                                },
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
                        tradeViewmodel.buyAsset(
                            asset = asset,
                            quantity = amountValue,
                            purchasePrice = asset.price
                        )
                    } else {
                        tradeViewmodel.sellAsset(
                            asset = asset,
                            quantity = amountValue,
                            sellPrice = asset.price
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isBuySelected) primary_color else Color.Red
                )
            ) {
                Text(
                    if (isBuySelected) stringResource(R.string.btn_buy) else stringResource(R.string.btn_sell),
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                )
            }
        }
    }
}

@Composable
fun TradeResultDialog(
    message: String,
    icon: ImageVector,
    iconColor: Color,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = message,
                    fontSize = 18.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

