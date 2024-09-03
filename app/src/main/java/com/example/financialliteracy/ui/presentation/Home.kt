package com.example.financialliteracy.ui.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.financialliteracy.R
import com.example.financialliteracy.model.Category
import com.example.financialliteracy.ui.theme.category_item1_color
import com.example.financialliteracy.ui.theme.category_item2_color
import com.example.financialliteracy.ui.theme.category_item3_color
import com.example.financialliteracy.ui.theme.category_item4_color
import com.example.financialliteracy.ui.theme.category_item5_color
import com.example.financialliteracy.ui.theme.category_item6_color
import com.example.financialliteracy.ui.theme.primary_color
import com.google.gson.Gson
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, paddingValues: PaddingValues) {
    val categoryList = remember { mutableStateListOf<Category>() }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        val category1 = Category(1, "Budget Planning")
        val category2 = Category(2, "Investing")
        val category3 = Category(3, "Savings")
        val category4 = Category(3, "Retirement Planning")
        val category5 = Category(3, "Debt Management")
        val category6 = Category(3, "Credit Scores")
        categoryList.add(category1)
        categoryList.add(category2)
        categoryList.add(category3)
        categoryList.add(category4)
        categoryList.add(category5)
        categoryList.add(category6)

    }
    Scaffold(
        topBar ={ CenterAlignedTopAppBar(title = { Text(text = "Financial Literacy", fontWeight = FontWeight.Bold) },
            colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = primary_color, titleContentColor = Color.White)
            )}

    ) { paddingValues ->
        val colors = listOf(category_item1_color, category_item2_color, category_item3_color, category_item4_color, category_item5_color,
            category_item6_color)

        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2),
        ) {
            items(categoryList.count(),
                itemContent = {
                    val category = categoryList[it]
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
                                .clickable {
                                },
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text =category.name,
                                fontSize = 25.sp,
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
        Screen.Home,
        Screen.List,
        Screen.Profile,
    )
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route) {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Image(
                        modifier = Modifier.size(27.dp),
                        painter = painterResource(id = item.iconId),
                        contentDescription = item.label
                    )
                },
                label = { Text(text = item.label) }
            )
        }

    }
}