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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.financialliteracy.ui.theme.primary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryDetail(
    navController: NavController,
    categoryId: String?,
) {
   val categoryDetailViewmodel: CategoryDetailViewmodel = hiltViewModel()

    val categoryDetail by categoryDetailViewmodel.categoryDetail.collectAsState(initial = null)

    LaunchedEffect(key1 = categoryId) {
        categoryId?.let {
            categoryDetailViewmodel.getCategoryDetail(it)
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = categoryDetail?.title ?: "Loading...",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = primary_color, titleContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (categoryDetail != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(horizontal = 15.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = categoryDetail?.title ?: "",
                        textAlign = TextAlign.Start,
                        fontSize = 26.sp,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                    Text(
                        text = categoryDetail?.description ?: "",
                        textAlign = TextAlign.Start
                    )
                }
            } else {
                // Eğer veri yükleniyorsa loading gösterebiliriz.
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            }
        }
    }
}
