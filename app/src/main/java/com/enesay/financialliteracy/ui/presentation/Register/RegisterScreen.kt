package com.enesay.financialliteracy.ui.presentation.Register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.ui.components.SimpleOutlinedButton
import com.enesay.financialliteracy.ui.theme.primary_color


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(navController: NavController) {

    val registerViewmodel: RegisterViewmodel = hiltViewModel()
    val authState by registerViewmodel.authState

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        val email = rememberSaveable { mutableStateOf("") }
        val password = rememberSaveable { mutableStateOf("") }
        val name = rememberSaveable { mutableStateOf("") }
        val surname = rememberSaveable { mutableStateOf("") }
        val emailError = rememberSaveable { mutableStateOf<String?>(null) }
        val passwordError = rememberSaveable { mutableStateOf<String?>(null) }
        val nameError = rememberSaveable { mutableStateOf<String?>(null) }
        val surnameError = rememberSaveable { mutableStateOf<String?>(null) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(bottom = 10.dp),
                    text = "Sign up",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Medium,
                    color = primary_color
                )
                CustomTextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        nameError.value = null
                    },
                    label = "Name",
                    isError = nameError.value != null,
                    errorMessage = nameError.value
                )

                CustomTextField(
                    value = surname.value,
                    onValueChange = {
                        surname.value = it
                        surnameError.value = null
                    },
                    label = "Surname",
                    isError = surnameError.value != null,
                    errorMessage = surnameError.value
                )

                CustomTextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailError.value = null
                    },
                    label = "Email",
                    isError = emailError.value != null,
                    errorMessage = emailError.value
                )

                CustomTextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordError.value = null
                    },
                    label = "Password",
                    isError = passwordError.value != null,
                    errorMessage = passwordError.value
                )
                Spacer(modifier = Modifier.size(23.dp))

                // Auth State Handling
                when (authState) {
                    is AuthState.Idle ->
                        SimpleOutlinedButton(
                            text = stringResource(R.string.btn_register),
                            onClick = {
                                if (email.value.isBlank()) {
                                    emailError.value = "Email cannot be empty"
                                }
                                if (password.value.isBlank()) {
                                    passwordError.value = "Password cannot be empty"
                                }
                                if (surname.value.isBlank()) {
                                    surnameError.value = "Surname cannot be empty"
                                }
                                if (name.value.isBlank()) {
                                    nameError.value = "Name cannot be empty"
                                }
                                if (emailError.value == null && passwordError.value == null) {
                                    registerViewmodel.signUp(
                                        email.value,
                                        password.value,
                                        name.value,
                                        surname.value
                                    )
                                }
                            },
                        )

                    is AuthState.Loading -> CircularProgressIndicator()

                    is AuthState.Authenticated -> {
                        navController.navigate("home") {
                            popUpTo("register") {
                                inclusive = true
                            }
                        }
                    }

                    is AuthState.Error -> {
                        Text(text = (authState as AuthState.Error).message, color = Color.Red)

                        SimpleOutlinedButton(
                            text = stringResource(R.string.btn_retry),
                            onClick = {
                                registerViewmodel.signUp(
                                    email.value,
                                    password.value,
                                    name.value,
                                    surname.value
                                )
                            },
                            borderColor = MaterialTheme.colorScheme.error,
                            textColor = MaterialTheme.colorScheme.error
                        )
                    }
                }

                SimpleOutlinedButton(
                    text = stringResource(R.string.txt_cancel),
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("register")
                        }
                    },
                    borderColor = MaterialTheme.colorScheme.error,
                    textColor = MaterialTheme.colorScheme.error
                )

                TextButton(
                    onClick = {
                        navController.navigate("home")
                    },
                    shape = RoundedCornerShape(22.dp), // Modern rounded corners
                    border = BorderStroke(
                        1.2.dp,
                        MaterialTheme.colorScheme.primary
                    ), // Customize border
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        disabledContentColor = MaterialTheme.colorScheme.onPrimary.copy(
                            alpha = 0.4f
                        )
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(top = 20.dp)
                        .wrapContentSize()// Fixed height for consistency
                ) {
                    Text(
                        modifier = Modifier.padding(5.dp),
                        text = stringResource(R.string.txt_continue_without_register),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    isError: Boolean,
    errorMessage: String?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            label = { Text(label) },
            isError = isError,
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                focusedBorderColor = Color.Gray,
                unfocusedBorderColor = Color.Gray,
                errorBorderColor = Color.Red,
                errorLabelColor = Color.Red,
            ),
            modifier = Modifier.fillMaxWidth()
        )

        if (isError && errorMessage != null) {
            Text(
                text = errorMessage,
                color = Color.Red,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 8.dp, top = 4.dp)
            )
        }
    }
}
