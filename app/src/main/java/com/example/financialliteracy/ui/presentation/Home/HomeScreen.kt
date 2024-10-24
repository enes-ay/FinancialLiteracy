package com.example.financialliteracy.ui.presentation.Home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.financialliteracy.model.Category
import com.example.financialliteracy.ui.presentation.Screen
import com.example.financialliteracy.ui.theme.category_item1_color
import com.example.financialliteracy.ui.theme.category_item2_color
import com.example.financialliteracy.ui.theme.category_item3_color
import com.example.financialliteracy.ui.theme.category_item4_color
import com.example.financialliteracy.ui.theme.category_item5_color
import com.example.financialliteracy.ui.theme.category_item6_color
import com.example.financialliteracy.ui.theme.primary_color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController, paddingValues: PaddingValues) {
    val homeViewmodel : HomeViewmodel = hiltViewModel()
    val educationalContentList by homeViewmodel.educationalContent.collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {

    }
    Scaffold(topBar = {
        CenterAlignedTopAppBar(
            title = { Text(text = "Financial Literacy", fontWeight = FontWeight.Bold) },
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
        Screen.Home,
        Screen.List,
        Screen.Profile,
        Screen.Portolio
    )
    NavigationBar(
        containerColor = Color.White,
        contentColor = Color.Black,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        items.forEach { item ->
            NavigationBarItem(selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
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
                label = { Text(text = item.label) })
        }

    }
}