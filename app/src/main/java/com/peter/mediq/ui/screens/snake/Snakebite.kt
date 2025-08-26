package com.peter.mediq.ui.screens.snake

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.peter.mediq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnakeBiteScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Snake Bite", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF8E24AA),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFF3E5F5), Color.White)
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.ic_snake_bite),
                contentDescription = "Snake Bite",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Title
            Text(
                text = "First Aid for Snake Bite",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF6A1B9A)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Steps
            val steps = listOf(
                "Keep the victim calm and still.",
                "Keep the bite area below the heart level.",
                "Do not suck the venom out or cut the wound.",
                "Remove tight clothing or jewelry near the bite.",
                "Seek immediate medical attention."
            )

            steps.forEachIndexed { index, step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF8EAF6))
                ) {
                    Text(
                        text = "${index + 1}. $step",
                        modifier = Modifier.padding(14.dp),
                        fontSize = 16.sp,
                        color = Color(0xFF4A148C)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Extra Note
            Text(
                text = "⚠️ Do not attempt to catch the snake. Take note of its color and shape to inform medical professionals.",
                fontSize = 14.sp,
                color = Color(0xFF555555),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
