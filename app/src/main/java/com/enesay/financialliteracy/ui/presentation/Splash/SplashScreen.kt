package com.enesay.financialliteracy.ui.presentation.Splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.ui.presentation.Login.LoginViewmodel
import kotlinx.coroutines.delay

@Composable
fun Splash(navController:NavController) {
    val loginViewmodel: LoginViewmodel= hiltViewModel()

    LaunchedEffect(Unit) {
        val isLoggedIn = loginViewmodel.userLoggedIn.value
        delay(2000L)
        if (isLoggedIn) {
            navController.navigate("home"){
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login"){
                popUpTo("splash") { inclusive = true }

            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary),
        contentAlignment = Alignment.Center
    ) {
        Text(stringResource(R.string.app_name),
            fontWeight = FontWeight.Bold,
            fontSize = 38.sp, color = Color.White)
    }
}