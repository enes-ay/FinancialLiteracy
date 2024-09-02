package com.example.financialliteracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.financialliteracy.ui.presentation.BottomBar
import com.example.financialliteracy.ui.presentation.Home
import com.example.financialliteracy.ui.presentation.Navigation
import com.example.financialliteracy.ui.theme.FinancialLiteracyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            FinancialLiteracyTheme {

                Scaffold( modifier = Modifier.fillMaxSize(),
                    bottomBar = { BottomBar(navController = navController) }) { paddingValues ->

                    Navigation(navController = navController,paddingValues = paddingValues)

                }

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinancialLiteracyTheme {
    }
}