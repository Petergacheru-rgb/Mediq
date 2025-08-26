package com.peter.mediq.ui.screens.seizure

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
fun SeizureScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Seizure", fontWeight = FontWeight.Bold, fontSize = 20.sp) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4CAF50),
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
                        colors = listOf(Color(0xFFE8F5E9), Color(0xFFFFFFFF))
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.ic_seizure),
                contentDescription = "Seizure",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .padding(8.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Title
            Text(
                "First Aid for Seizure",
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color(0xFF2E7D32)
            )

            Spacer(Modifier.height(12.dp))

            // Steps
            val steps = listOf(
                "Clear the area of dangerous objects.",
                "Place something soft under the head.",
                "Do not put anything in the mouth.",
                "Turn them onto their side after seizure.",
                "Stay with them until fully alert."
            )

            steps.forEachIndexed { index, step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9))
                ) {
                    Text(
                        text = "${index + 1}. $step",
                        modifier = Modifier.padding(14.dp),
                        fontSize = 16.sp,
                        color = Color(0xFF1B5E20)
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // Note
            Text(
                text = "⚠️ Stay calm and reassure others around. Seek medical help if the seizure lasts more than 5 minutes or repeats immediately.",
                fontSize = 14.sp,
                color = Color(0xFF555555),
                fontWeight = FontWeight.Medium
            )
        }
    }
}
