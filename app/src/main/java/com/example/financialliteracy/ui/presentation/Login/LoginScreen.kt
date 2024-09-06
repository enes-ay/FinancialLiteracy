package com.example.financialliteracy.ui.presentation.Login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financialliteracy.ui.presentation.Register.AuthState
import com.example.financialliteracy.ui.presentation.Register.RegisterViewmodel
import kotlin.math.log

@Composable
fun Login(navController: NavController,
           loginViewmodel: LoginViewmodel
) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState

    Scaffold (modifier = Modifier.fillMaxSize()) { paddingValues ->

        Column (
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            Column (modifier= Modifier.size(300.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally) {
                TextField(value = email.value, onValueChange = { email.value = it }, label = { Text("email") })
                TextField(value = password.value, onValueChange = { password.value = it }, label = { Text("password") })
                when (authState) {
                    is AuthState.Idle -> Button(onClick = {
                        loginViewmodel.signIn(
                            email.value,
                            password.value
                        )
                    }) {
                        Text("Login")
                    }

                    is AuthState.Loading -> CircularProgressIndicator()
                    is AuthState.Authenticated -> {Text(text = "Login is Successful!")
                        navController.navigate("home")
                    }
                    is AuthState.Error -> {
                        Text(text = (authState as AuthState.Error).message, color = Color.Red)
                        Button(onClick = {
                            loginViewmodel.signIn(
                                email.value,
                                password.value
                            )
                        }) {
                            Text("Retry")
                        }
                    }

                }
            }

        }

    }
}