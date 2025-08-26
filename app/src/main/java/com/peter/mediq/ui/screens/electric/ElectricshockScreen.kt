package com.peter.mediq.ui.screens.electric

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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.peter.mediq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ElectricShockScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Electric Shock",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFB71C1C), // Red danger color
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color(0xFFFFEBEE)) // Soft red background
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top image
            Image(
                painter = painterResource(id = R.drawable.ic_shock),
                contentDescription = "Electric Shock",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Heading
            Text(
                text = "First Aid for Electric Shock",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFFD32F2F)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Follow these steps carefully:",
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Steps as styled cards
            val instructions = listOf(
                "Do not touch the person if they are still in contact with the electrical source.",
                "Turn off the power source if possible (unplug, switch off the breaker).",
                "If you can't turn off the power, use a non-conductive object (like wood or plastic) to move the person away.",
                "Once safe, check for breathing and pulse. If absent, begin CPR immediately.",
                "Cover any visible burns with a sterile cloth.",
                "Seek medical attention even if the person seems fine — internal injuries may exist."
            )

            instructions.forEachIndexed { index, step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}.",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFD32F2F),
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = step,
                            fontSize = 15.sp,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Note section
            Text(
                text = "⚠️ Note: Do not move the person unless absolutely necessary. Electrical shocks can cause spinal injuries.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFB71C1C)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ElectricShockScreenPreview() {
    ElectricShockScreen(navController = rememberNavController())
}
