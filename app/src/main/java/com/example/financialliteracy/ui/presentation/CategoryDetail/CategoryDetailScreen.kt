package com.example.financialliteracy.ui.presentation.CategoryDetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.financialliteracy.ui.theme.primary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetail(navController: NavController, categoryId:String?) {

    LaunchedEffect(key1 = true) {
        // viewmodel.getDetail(categoryId)
        Log.e("gelen", categoryId.toString())
    }
    Scaffold (topBar = { CenterAlignedTopAppBar(title = {
        androidx.compose.material3.Text(
            text = "Category Name Here $categoryId",
            fontWeight = FontWeight.Bold
        )
    },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primary_color, titleContentColor = Color.White)
    )}) { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally) {

           Column (modifier = Modifier.fillMaxWidth().height(400.dp).padding(horizontal = 15.dp, vertical = 10.dp)) {
               Text(text = "Budget Planning", textAlign = TextAlign.Start, fontSize = 26.sp, modifier = Modifier.padding(vertical = 10.dp))
               Text(text = "Budgeting is a proactive approach to organizing your finances. " +
                       "A budget tracks what you earn and what you spend and ensures you have" +
                       " more money coming in than you have going out. This allows you to cover" +
                       " the costs of living, to afford the things that are " +
                       "important to you, and to plan toward short- and long-term goals. ")
           }
        }
    }

}