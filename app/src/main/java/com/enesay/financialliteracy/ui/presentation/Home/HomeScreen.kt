package com.enesay.financialliteracy.ui.presentation.Home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.enesay.financialliteracy.R
import com.enesay.financialliteracy.model.Education.EducationalContent
import com.enesay.financialliteracy.model.Trade.Asset
import com.enesay.financialliteracy.ui.theme.AssetCardColor
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
            title = {
                Text(
                    text = stringResource(R.string.app_name), fontWeight = FontWeight.Bold
                )
            }, colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val context = LocalContext.current
            Column(
                modifier = Modifier
                    .wrapContentSize()
                    .padding(5.dp)
            ) {
                Text(
                    modifier = Modifier.padding(4.dp),
                    text = "Lessons",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                FinancialLessonList(educationalContentList, onLessonClick = {
                    navController.navigate("categoryDetail/$it")
                })
            }

            ProfitLossCard(123.34)
            AssetCard(
                asset = Asset(
                    "1",
                    "Bitcoin",
                    "BTC",
                    97000.0,
                    1.2,
                    234232.2,
                    1,
                    124125125215212.32,
                    124312424124124.21
                )
            )

            LazyVerticalGrid(
                modifier = Modifier.wrapContentSize(),
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
                                text = content.content_name,
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
}

@Composable
fun FinancialLessonList(
    lessons: List<EducationalContent>, onLessonClick: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(lessons) { lesson ->
            LessonCard(lessonTitle = lesson.content_name, onClick = { onLessonClick(lesson.id) })
        }
    }
}

@Composable
fun LessonCard(
    lessonTitle: String, onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = lessonTitle,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 10.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfitLossCard(totalProfitLoss: Double) {
    val isProfit = totalProfitLoss >= 0
    val icon = if (isProfit) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    val backgroundColor = if (isProfit) Brush.horizontalGradient(
        colors = listOf(Color(0xFFDFF6E1), Color(0xFFB2DFDB))
    ) else Brush.horizontalGradient(
        colors = listOf(Color(0xFFFFE5E5), Color(0xFFFFCDD2))
    )
    val textColor = if (isProfit) Color(0xFF2E7D32) else Color(0xFFC62828)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(120.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Icon
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = textColor,
                    modifier = Modifier.size(36.dp)
                )

                // Profit/Loss Text
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = if (isProfit) "Profit" else "Loss",
                        fontWeight = FontWeight.Normal,
                        color = textColor,
                        fontSize = 35.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "$${String.format("%.2f", kotlin.math.abs(totalProfitLoss))}",
                        fontWeight = FontWeight.Normal,
                        color = textColor,
                        fontSize = 26.sp
                    )
                }
            }
        }
    }
}

@Composable
fun AssetCard(asset: Asset) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(100.dp)
            .clickable { },
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(6.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AssetCardColor),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = asset.symbol,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
                Text(
                    text = "$${String.format("%.2f", asset.price * asset.quantity)}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 23.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = asset.symbol,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 27.sp
                )
                Text(
                    text = "$${String.format("%.2f", asset.price * asset.quantity)}",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

        }
    }
}
