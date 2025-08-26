package com.peter.mediq.ui.screens.home

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.airbnb.lottie.compose.*
import com.peter.mediq.Navigation.*
import com.peter.mediq.R

@Composable
fun HomeScreen(navController: NavController) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.first_aid_anim))
    val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFDE0B0B), Color(0xFFF6F5F5))
                )
            )
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    LottieAnimation(
                        composition = composition,
                        progress = progress,
                        modifier = Modifier.size(220.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Mediq Emergency",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            val dashboardItems = listOf(
                DashboardItem("Emergency Help", Icons.Default.Warning, listOf(Color(0xFFFF416C), Color(0xFFFF4B2B)), ROUTE_EMERGENCYHELP),
                DashboardItem("First Aid Guide", Icons.Default.Info, listOf(Color(0xFF36D1DC), Color(
                    0xFF0F5A93
                )
                ), ROUTE_FIRSTAID),
                DashboardItem("Call Emergency", Icons.Default.Call, listOf(Color(0xFFFFB347), Color(0xFFFFCC33)), ROUTE_CALL)
            )

            items(dashboardItems) { item ->
                DashboardCard(item = item, navController = navController)
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Text(
                    text = "Emergency Tips & Alerts",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(12.dp))
            }

            val emergencyList = listOf(
                EmergencyCardData(
                    "Severe Weather Alert",
                    "Heavy rainfall expected in your area. Stay indoors and avoid travel if possible.",
                    R.drawable.severe
                ),
                EmergencyCardData(
                    "Road Safety Tip",
                    "Always wear a seatbelt while driving or riding in a car. Follow speed limits and avoid using your phone.",
                    R.drawable.road
                )

            )

            items(emergencyList) { item ->
                EmergencyInfoCard(title = item.title, description = item.description, imageRes = item.imageRes)
            }
        }
    }
}

@Composable
fun DashboardCard(item: DashboardItem, navController: NavController) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp))
            .clickable { navController.navigate(item.route) }
            .background(
                brush = Brush.horizontalGradient(item.gradientColors),
                shape = RoundedCornerShape(20.dp)
            )
            .animateContentSize(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(20.dp)
        ) {
            Icon(item.icon, contentDescription = item.title, tint = Color.White, modifier = Modifier.size(40.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Text(item.title, color = Color.White, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun EmergencyInfoCard(title: String, description: String, imageRes: Int) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(190.dp)
            .shadow(8.dp, RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box {
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f))
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = description, fontSize = 14.sp, color = Color.White)
            }
        }
    }
}

data class DashboardItem(val title: String, val icon: ImageVector, val gradientColors: List<Color>, val route: String)
data class EmergencyCardData(val title: String, val description: String, val imageRes: Int)

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(rememberNavController())
}
