package com.enesay.financialliteracy.ui.presentation.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.ui.presentation.Screens
import com.enesay.financialliteracy.ui.theme.category_item1_color
import com.enesay.financialliteracy.ui.theme.category_item2_color
import com.enesay.financialliteracy.ui.theme.category_item3_color
import com.enesay.financialliteracy.ui.theme.category_item4_color
import com.enesay.financialliteracy.ui.theme.category_item5_color
import com.enesay.financialliteracy.ui.theme.category_item6_color
import com.enesay.financialliteracy.ui.theme.primary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {

    val homeViewmodel: HomeViewmodel = hiltViewModel()
    val educationalContentList by homeViewmodel.educationalContent.collectAsState(initial = emptyList())

    Scaffold(topBar = {
        TopAppBar(
            title = { Text(text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                containerColor = primary_color, titleContentColor = Color.White
            )
        )
    }

    ) { paddingValues ->
        val colors = listOf(
            category_item1_color,
            category_item2_color,
            category_item3_color,
            category_item4_color,
            category_item5_color,
            category_item6_color
        )

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
        ) {
            items(educationalContentList.count(), itemContent = {
                val content = educationalContentList[it]
                val color = colors[it % colors.size]
                Card(
                    modifier = Modifier
                        .padding(all = 5.dp)
                        .size(250.dp, 150.dp)
                        .padding(5.dp),
                    shape = RoundedCornerShape(7.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 10.dp)
                            .clickable {
                                navController.navigate("categoryDetail/${content.id}")
                            },
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = content.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            })
        }
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val items = listOf(
        Screens.Home,
        Screens.List,
        Screens.Profile,
        Screens.Portolio
    )
    NavigationBar(
        modifier = Modifier.border(
            BorderStroke(1.dp, Color.Gray.copy(alpha = 0.2f)), // Şeffaf bir gri sınır
            shape = CutCornerShape(
                topStart = 0.dp,
                topEnd = 0.dp,
                bottomEnd = 0.dp,
                bottomStart = 0.dp
            )
        ),
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                  Icon(
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label, fontSize = 13.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = primary_color,
                    selectedTextColor = primary_color,
                    indicatorColor = Color.Transparent
                )
            )
        }
    }
}