package com.peter.mediq.ui.screens.emergency

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peter.mediq.Navigation.*
import com.peter.mediq.R

data class EmergencyItem(val title: String, val icon: Int)

@Composable
fun EmergencyHelpScreen(navController: NavController) {
    val emergencyList = listOf(
        EmergencyItem("CPR", R.drawable.ic_cpr),
        EmergencyItem("Bleeding", R.drawable.ic_bleeding),
        EmergencyItem("Burns", R.drawable.ic_burns),
        EmergencyItem("Choking", R.drawable.ic_choking),
        EmergencyItem("Electric Shock", R.drawable.ic_shock),
        EmergencyItem("Head Injury", R.drawable.ic_head_injury),
        EmergencyItem("Fracture", R.drawable.ic_fracture),
        EmergencyItem("Snake Bite", R.drawable.ic_snake_bite),
        EmergencyItem("Drowning", R.drawable.ic_drowning),
        EmergencyItem("Seizure", R.drawable.ic_seizure),
        EmergencyItem("Hypothermia", R.drawable.ic_hypothermia),
        EmergencyItem("Heat Stroke", R.drawable.ic_heat_stroke)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFFBBDEFB), Color(0xFFE3F2FD)) // Soft blue gradient
                )
            )
            .padding(16.dp)
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Emergency Help",
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    color = Color(0xFF0D47A1),
                    modifier = Modifier.padding(bottom = 12.dp)
                )
            }

            items(emergencyList) { item ->
                EmergencyCard(item) {
                    when (item.title) {
                        "CPR" -> navController.navigate(ROUTE_CPR)
                        "Bleeding" -> navController.navigate(ROUTE_BLEEDING)
                        "Burns" -> navController.navigate(ROUTE_BURNS)
                        "Choking" -> navController.navigate(ROUTE_CHOKING)
                        "Electric Shock" -> navController.navigate(ROUTE_ELECTRIC)
                        "Head Injury" -> navController.navigate(ROUTE_HEAD)
                        "Fracture" -> navController.navigate(ROUTE_FRACTURE)
                        "Snake Bite" -> navController.navigate(ROUTE_SNAKE)
                        "Drowning" -> navController.navigate(ROUTE_DROWNING)
                        "Seizure" -> navController.navigate(ROUTE_SEIZURE)
                        "Hypothermia" -> navController.navigate(ROUTE_HYPOTHERMIA)
                        "Heat Stroke" -> navController.navigate(ROUTE_HEAT)
                    }
                }
            }
        }
    }
}

@Composable
fun EmergencyCard(item: EmergencyItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(18.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = item.title,
                modifier = Modifier
                    .size(56.dp)
                    .padding(end = 16.dp)
            )
            Text(
                text = item.title,
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                ),
                color = Color(0xFF0D47A1) // Deep blue text
            )
        }
    }
}
