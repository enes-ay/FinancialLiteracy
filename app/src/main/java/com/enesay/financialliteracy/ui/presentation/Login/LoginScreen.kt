package com.enesay.financialliteracy.ui.presentation.Login

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesay.financialliteracy.ui.presentation.Register.AuthState
import com.enesay.financialliteracy.ui.theme.primary_color
import com.enesay.financialliteracy.utils.DataStoreHelper
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState
    val userPreferencesDataStore = DataStoreHelper(context = LocalContext.current)

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Collect DataStore values
            val rememberMe = userPreferencesDataStore.rememberMeFlow.collectAsState(initial = false)
            val email = userPreferencesDataStore.emailFlow.collectAsState(initial = "")
            val password = userPreferencesDataStore.passwordFlow.collectAsState(initial = "")

            // States for input fields
            val emailState = rememberSaveable { mutableStateOf(email.value ?: "") }
            val passwordState = rememberSaveable { mutableStateOf(password.value ?: "") }
            val rememberMeState = rememberSaveable { mutableStateOf(rememberMe.value) }

            val emailError = rememberSaveable { mutableStateOf<String?>(null) }
            val passwordError = rememberSaveable { mutableStateOf<String?>(null) }
            val scope = rememberCoroutineScope()

            LaunchedEffect(key1 = rememberMe.value, key2 = email.value, key3 = password.value) {
                Log.e("launch", "triggered")
                emailState.value = email.value ?: ""
                passwordState.value = password.value ?: ""
                rememberMeState.value = rememberMe.value
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 20.dp),
                        text = "Sign in",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Medium,
                        color = primary_color
                    )

                    // E-mail field
                    OutlinedTextField(
                        value = emailState.value,
                        onValueChange = {
                            emailState.value = it
                            emailError.value = null
                        },
                        label = { Text("Email") },
                        isError = emailError.value != null,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (emailError.value != null) {
                        Text(text = emailError.value!!, color = Color.Red, fontSize = 12.sp)
                    }
                    // Password Field
                    OutlinedTextField(
                        value = passwordState.value,
                        onValueChange = {
                            passwordState.value = it
                            passwordError.value = null
                        },
                        label = { Text("Password") },
                        isError = passwordError.value != null,
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            focusedBorderColor = Color.Gray,
                            unfocusedBorderColor = Color.Gray,
                            errorBorderColor = Color.Red,
                            errorLabelColor = Color.Red,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    if (passwordError.value != null) {
                        Text(text = passwordError.value!!, color = Color.Red, fontSize = 12.sp)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            colors = CheckboxDefaults.colors(checkedColor = primary_color),
                            checked = rememberMeState.value,
                            onCheckedChange = {
                                rememberMeState.value = it
                            })
                        Text("Remember me", fontSize = 18.sp)
                    }

                    when (authState) {
                        is AuthState.Idle -> Button(
                            onClick = {
                                if (emailState.value.isBlank()) {
                                    emailError.value = "Email cannot be empty"
                                }
                                if (passwordState.value.isBlank()) {
                                    passwordError.value = "Password cannot be empty"
                                }
                                if (emailError.value == null && passwordError.value == null) {
                                    loginViewmodel.signIn(emailState.value, passwordState.value)
                                }
                            },
                            modifier = Modifier.width(300.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primary_color,
                            )
                        ) {
                            Text("Sign in", fontSize = 17.sp, fontWeight = FontWeight.Medium)
                        }

                        is AuthState.Loading -> CircularProgressIndicator()

                        is AuthState.Authenticated -> {
                            Text(
                                text = "Login Successful!",
                                color = MaterialTheme.colorScheme.primary
                            )
                                LaunchedEffect(AuthState.Authenticated) {
                                    Log.e("launch", "auth state")
                                    scope.launch {
                                        if (rememberMeState.value) {
                                            userPreferencesDataStore.saveUserPreferences(
                                                rememberMeState.value,
                                                emailState.value,
                                                passwordState.value
                                            )
                                        }
                                        loginViewmodel.currentUser.value?.let {
                                            userPreferencesDataStore.saveUserId(
                                                it
                                            )
                                        }
                                    }
                                }

                            navController.navigate("home") {
                                popUpTo("login") { inclusive = true }
                            }
                        }

                        is AuthState.Error -> {
                            Text(
                                text = (authState as AuthState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                            Button(
                                onClick = {
                                    loginViewmodel.signIn(
                                        emailState.value,
                                        passwordState.value
                                    )
                                },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Retry", color = Color.White)
                            }
                        }
                    }

                    Row (modifier= Modifier.fillMaxWidth().padding(10.dp),
                        horizontalArrangement = Arrangement.Absolute.Center){
                        Text(
                            text = "Don't have an account? ",
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.secondary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = "Create here!",
                            modifier = Modifier
                                .clickable { navController.navigate("register") },
                            fontSize = 16.sp,
                            color = primary_color,
                            textAlign = TextAlign.Center
                        )

                    }
                }
            }
        }
    }
}