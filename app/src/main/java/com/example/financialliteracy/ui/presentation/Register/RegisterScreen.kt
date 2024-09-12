package com.example.financialliteracy.ui.presentation.Register

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController


@Composable
fun Register(modifier: Modifier = Modifier, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

    val registerViewmodel:RegisterViewmodel = hiltViewModel()
    val authState by registerViewmodel.authState

    Scaffold (modifier = Modifier.fillMaxSize()) { paddingValues ->

        val name = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val nameError = remember { mutableStateOf<String?>(null) }
        val emailError = remember { mutableStateOf<String?>(null) }
        val passwordError = remember { mutableStateOf<String?>(null) }

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.size(300.dp),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Name Field
                TextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        nameError.value = null // Değer değiştirildiğinde hatayı sıfırla
                    },
                    label = { Text("Name") },
                    isError = nameError.value != null // Hata durumu
                )
                if (nameError.value != null) {
                    Text(text = nameError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Email Field
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailError.value = null // Değer değiştirildiğinde hatayı sıfırla
                    },
                    label = { Text("Email") },
                    isError = emailError.value != null // Hata durumu
                )
                if (emailError.value != null) {
                    Text(text = emailError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Password Field
                TextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordError.value = null // Değer değiştirildiğinde hatayı sıfırla
                    },
                    label = { Text("Password") },
                    isError = passwordError.value != null // Hata durumu
                )
                if (passwordError.value != null) {
                    Text(text = passwordError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Auth State Handling
                when (authState) {
                    is AuthState.Idle -> Button(onClick = {
                        // Boş alan kontrolü yap
                        if (name.value.isBlank()) {
                            nameError.value = "Name cannot be empty"
                        }
                        if (email.value.isBlank()) {
                            emailError.value = "Email cannot be empty"
                        }
                        if (password.value.isBlank()) {
                            passwordError.value = "Password cannot be empty"
                        }

                        // Eğer hata yoksa kaydı başlat
                        if (nameError.value == null && emailError.value == null && passwordError.value == null) {
                            registerViewmodel.signUp(email.value, password.value)
                        }
                    }) {
                        Text("Register")
                    }

                    is AuthState.Loading -> CircularProgressIndicator()

                    is AuthState.Authenticated -> {
                        Text(text = "Registration is Successful!")
                        navController.navigate("home")
                    }

                    is AuthState.Error -> {
                        Text(text = (authState as AuthState.Error).message, color = Color.Red)
                        Button(onClick = {
                            registerViewmodel.signUp(email.value, password.value)
                        }) {
                            Text("Register")
                        }
                    }
                }
            }
        }

    }
}