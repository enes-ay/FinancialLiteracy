package com.example.financialliteracy.ui.presentation.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financialliteracy.R
import com.example.financialliteracy.ui.presentation.Login.LoginViewmodel
import com.example.financialliteracy.ui.presentation.Register.AuthState
import com.example.financialliteracy.ui.theme.primary_color
import com.example.financialliteracy.ui.theme.secondary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Profile(navController: NavController) {
    var showDialog by remember { mutableStateOf(false) }

    val mockList = remember {
        mutableStateListOf(
            "Edit Profile",
            "Favorite List",
            "FAQ",
            "Sign Out",
            "ABCD EFG",
            "SEDGSED",
            "WESDGSE",
            "SDGSDB",
            "SDBHSD"
        )
    }
    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState

    Scaffold(topBar = {
        CenterAlignedTopAppBar(
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
                    .height(300.dp)
                    .weight(1f)
                    .background(secondary_color),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .padding(vertical = 5.dp), verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image( Icons.Default.Person, contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .padding(10.dp)
                            .weight(2f)
                            .clickable {
                                showDialog=true
                            })

                    SignOutDialog(
                        showDialog = showDialog,
                        onDismiss = { showDialog = false },
                        onConfirm = {
                            loginViewmodel.signOut()
                            if (authState is AuthState.Idle) {
                                navController.navigate("login")
                            }
                            showDialog = false
                        },
                    )



                    Text(text = "Enes Ay", fontSize = 25.sp, fontWeight = FontWeight.Bold)
                }
                
                Column(modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "dsgsdg")
                    
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(4f), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(mockList.count()) {
                    val item = mockList[it]
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .clickable { }) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 30.dp, vertical = 20.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "$item", fontSize = 20.sp)
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "setting icon"
                            )

                        }
                    }
                }
            }

        }
    }

}

@Composable
fun SignOutDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = {  },
            title = { Text("Are you sure you want to log out?") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No")
                }
            }
        )
    }
}