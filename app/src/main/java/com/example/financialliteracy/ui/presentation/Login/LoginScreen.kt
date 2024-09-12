package com.example.financialliteracy.ui.presentation.Login

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
            val emailError = remember { mutableStateOf<String?>(null) }
            val passwordError = remember { mutableStateOf<String?>(null) }

            Column(
                modifier = Modifier.size(300.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailError.value = null // Kullanıcı değer değiştirdiğinde hatayı sıfırla
                    },
                    label = { Text("Email") },
                    isError = emailError.value != null // Hata durumu varsa kırmızı kenarlık
                )
                if (emailError.value != null) {
                    Text(text = emailError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                TextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordError.value = null // Kullanıcı değer değiştirdiğinde hatayı sıfırla
                    },
                    label = { Text("Password") },
                    isError = passwordError.value != null // Hata durumu varsa kırmızı kenarlık
                )
                if (passwordError.value != null) {
                    Text(text = passwordError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                when (authState) {
                    is AuthState.Idle -> Button(onClick = {
                        if (email.value.isBlank()) {
                            emailError.value = "Email cannot be empty"
                        }
                        if (password.value.isBlank()) {
                            passwordError.value = "Password cannot be empty"
                        }
                        if (emailError.value == null && passwordError.value == null) {
                            loginViewmodel.signIn(email.value, password.value)
                        }
                    }) {
                        Text("Login")
                    }

                    is AuthState.Loading -> CircularProgressIndicator()
                    is AuthState.Authenticated -> {
                        Text(text = "Login is Successful!")
                        navController.navigate("home"){
                            popUpTo("login") { inclusive = true }
                        }
                    }

                    is AuthState.Error -> {
                        Text(text = (authState as AuthState.Error).message, color = Color.Red)
                        Button(onClick = {
                            loginViewmodel.signIn(email.value, password.value)
                        }) {
                            Text("Retry")
                        }
                    }
                }

                Text(
                    text = "Don't have an account? Create here!",
                    modifier = Modifier
                        .padding(10.dp)
                        .clickable {
                            navController.navigate("register")
                        },
                    fontSize = 22.sp,
                    color = Color.Blue
                )
            }


        }

    }
}