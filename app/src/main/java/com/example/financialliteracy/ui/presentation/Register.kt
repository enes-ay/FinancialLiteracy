package com.example.financialliteracy.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun Register(modifier: Modifier = Modifier, navController: NavController) {
    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val name = remember { mutableStateOf("") }

   Scaffold (modifier = Modifier.fillMaxSize()) {paddingValues ->

       Column (
           Modifier
               .fillMaxSize()
               .padding(paddingValues),
           verticalArrangement = Arrangement.Center,
           horizontalAlignment = Alignment.CenterHorizontally) {

           Column (modifier=Modifier.size(300.dp),
               verticalArrangement = Arrangement.SpaceEvenly,
               horizontalAlignment = Alignment.CenterHorizontally) {
               TextField(value = name.value, onValueChange = { name.value = it }, label = {Text("name")})
               TextField(value = email.value, onValueChange = { email.value = it }, label = {Text("email")})
               TextField(value = password.value, onValueChange = { password.value = it }, label = {Text("password")})
               TextButton(onClick = {
                   navController.navigate("home")
               },
                   colors = ButtonDefaults.textButtonColors(containerColor = Color.Black, contentColor = Color.White)) {
                   Text(text = "Sign Up")
               }
           }

       }

   }
}