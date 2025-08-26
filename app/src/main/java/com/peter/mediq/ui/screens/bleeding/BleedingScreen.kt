package com.peter.mediq.ui.screens.bleeding

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.peter.mediq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BleedingScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Bleeding",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD32F2F) // deep red for urgency
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFF5F5F5)) // light background
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Image inside Card for modern look
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_bleeding),
                    contentDescription = "Bleeding Illustration",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Follow these steps carefully:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFFB71C1C), // darker red for emphasis
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            val instructions = listOf(
                "Apply firm, direct pressure to the wound with a clean cloth or bandage.",
                "Keep the pressure constant until the bleeding stops.",
                "Elevate the injured area above the heart if possible.",
                "Do not remove objects stuck in the wound. Apply pressure around them.",
                "Wrap the wound with a clean bandage once bleeding slows.",
                "Seek medical attention if the bleeding is severe or does not stop."
            )

            instructions.forEachIndexed { index, step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White)
                ) {
                    Text(
                        text = "${index + 1}. $step",
                        modifier = Modifier.padding(14.dp),
                        fontSize = 15.sp,
                        color = Color.Black
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Note: Use gloves if available to reduce infection risk. Avoid using a tourniquet unless trained.",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BleedingScreenPreview() {
    BleedingScreen(navController = rememberNavController())
}
