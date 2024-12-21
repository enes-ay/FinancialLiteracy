package com.enesay.financialliteracy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.enesay.financialliteracy.ui.components.BottomBar
import com.enesay.financialliteracy.ui.presentation.Navigation.Navigation
import com.enesay.financialliteracy.ui.presentation.Screens
import com.enesay.financialliteracy.ui.theme.FinancialLiteracyTheme
import com.enesay.financialliteracy.utils.DataStoreHelper
import com.enesay.financialliteracy.utils.setAppLocale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userPreferencesDataStore = DataStoreHelper(context = LocalContext.current)
            val navController = rememberNavController()
            val isDarkMode by userPreferencesDataStore.darkModeFlow.collectAsState(initial = false)

            FinancialLiteracyTheme(darkTheme = isDarkMode) {

                Scaffold(modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        val showBottomBar = shouldShowBottomBar(navController = navController)
                        if (showBottomBar) {
                            BottomBar(navController = navController)
                        }

                    }) { paddingValues ->

                    Navigation(navController = navController, paddingValues = paddingValues)
                }
            }
        }

        // DataStoreHelper örneği
        val userPreferencesDataStore = DataStoreHelper(context = this)

        // Kaydedilmiş dil tercihini uygulama diline ayarlayın
        lifecycleScope.launch {
            userPreferencesDataStore.languageFlow.collect { savedLanguage ->
                if (savedLanguage != null) {
                    setAppLocale(this@MainActivity, savedLanguage)
                }
                else{
                    setAppLocale(this@MainActivity, "en")
                }
            }
        }
    }
}

@Composable
private fun shouldShowBottomBar(navController: NavController): Boolean {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    return when (currentDestination?.route) {
        Screens.Home.route, Screens.List.route, Screens.Profile.route, Screens.Portolio.route -> true // Replace with your routes
        else -> false
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FinancialLiteracyTheme {
    }
}