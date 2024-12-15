package com.enesay.financialliteracy.ui.presentation.Profile

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesay.financialliteracy.MainActivity
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.ui.components.SimpleDialog
import com.enesay.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.enesay.financialliteracy.ui.presentation.Register.AuthState
import com.enesay.financialliteracy.ui.theme.primary_color
import com.enesay.financialliteracy.ui.theme.secondary_color
import com.enesay.financialliteracy.utils.DataStoreHelper
import com.enesay.financialliteracy.utils.setAppLocale
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController, context: Context) {
    var showDialog by remember { mutableStateOf(false) }
    val userPreferencesDataStore = DataStoreHelper(context = LocalContext.current)

    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val userInfo by loginViewmodel.userInfo
    val authState by loginViewmodel.authState
    val scope = rememberCoroutineScope()
    var isDarkMode by remember { mutableStateOf(false) }
    var showLanguageDialog by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("English") } // Default language

    LaunchedEffect(Unit) {
        loginViewmodel.getUserInfo()
    }
    if (showLanguageDialog) {
        LanguageSelectionDialog(
            currentLanguage = selectedLanguage,
            onLanguageSelected = { language ->
                selectedLanguage = language
                showLanguageDialog = false

                    // Dil değişikliği işlemi
                    val newContext = context.setAppLocale(language)
                    val intent = Intent(newContext, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    context.startActivity(intent)
            },
            onDismissRequest = { showLanguageDialog = false }
        )
    }

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = "Profile", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = primary_color, titleContentColor = Color.White
            )
        )
    }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(16.dp) // Add padding around the Row
                    .background(Color.Transparent), // Remove unnecessary solid background
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Profile Section
                Card(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(containerColor = secondary_color),// Add padding inside the card
                    elevation = CardDefaults.cardElevation(8.dp), // Add elevation for shadow
                    shape = RoundedCornerShape(16.dp) // Rounded corners for a modern look
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Image(
                            Icons.Default.Person, // Placeholder for profile image
                            contentDescription = "Profile Icon",
                            modifier = Modifier
                                .size(80.dp) // Larger size for profile picture
                                .clip(CircleShape) // Circular profile picture
                                .background(secondary_color.copy(alpha = 0.2f)) // Subtle background
                                .padding(16.dp)
                        )
                        SimpleDialog(
                            title = stringResource(R.string.logout_title),
                            message = stringResource(R.string.logout_message),
                            showDialog = showDialog,
                            onDismiss = { showDialog = false },
                            onConfirm = {
                                loginViewmodel.signOut()
                                if (authState is AuthState.Idle) {
                                    navController.navigate("login") {
                                        popUpTo(0) {
                                            inclusive = true
                                        }
                                    }
                                    scope.launch {
                                        userPreferencesDataStore.clearUserId()
                                    }
                                }
                                showDialog = false
                            },
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Column {
                            Text(
                                text = "${userInfo?.name} ${userInfo?.surname}",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = userInfo?.email ?: "Loading...",
                                fontSize = 16.sp,
                                color = Color.Gray
                            )
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    ProfileItemsRow("Edit Profile", onClick = {})
                }
                item {
                    ProfileItemsRow("FAQ", onClick = {})
                }
                item {
                    ProfileItemsRow(stringResource(R.string.change_language), onClick = {
                        showLanguageDialog = true
                    })
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = stringResource(R.string.switch_dark_mode), fontSize = 20.sp)

                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { isChecked ->
                                isDarkMode = isChecked
                                // Handle dark mode toggle logic here
                            }
                        )
                    }
                }
                item {
                    ProfileItemsRow("Log out", onClick = {
                        showDialog = true
                    }, color = Color.Red, showIcon = false)
                }
            }
        }
    }

}

@Composable
private fun ProfileItemsRow(
    title: String,
    onClick: () -> Unit,
    color: Color = Color.Black,
    showIcon: Boolean = true
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 20.dp).padding(end = 13.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, fontSize = 20.sp, color = color)
            if (showIcon) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "setting icon",
                    tint = color
                )
            }
        }
    }
}

@Composable
fun LanguageSelectionDialog(
    currentLanguage: String,
    onLanguageSelected: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        title = {
            Text(
                text = stringResource(R.string.change_language_title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Column (modifier = Modifier.padding(top = 10.dp)) {
                LanguageOption(
                    language = "English",
                    isSelected = currentLanguage == "English",
                    onClick = { onLanguageSelected("English") }
                )
                LanguageOption(
                    language = "Turkish",
                    isSelected = currentLanguage == "Turkish",
                    onClick = { onLanguageSelected("Turkish") }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text(text = stringResource(R.string.txt_cancel))
            }
        }
    )
}

@Composable
fun LanguageOption(
    language: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp).padding(top = 2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onClick
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = language,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}