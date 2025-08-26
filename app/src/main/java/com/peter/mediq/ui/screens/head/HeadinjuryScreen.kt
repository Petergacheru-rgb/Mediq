package com.peter.mediq.ui.screens.head

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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.peter.mediq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeadInjuryScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "🤕 Head Injury",
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
                    containerColor = Color(0xFFD32F2F) // Red danger tone
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFFFFEBEE), Color(0xFFFFCDD2)) // light red gradient
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.Start
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.ic_head_injury),
                contentDescription = "Head Injury",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .padding(bottom = 16.dp)
            )

            // Title
            Text(
                text = "Follow these steps carefully:",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFFD32F2F),
                modifier = Modifier.padding(bottom = 12.dp)
            )

            // Instructions styled as cards
            val instructions = listOf(
                "Keep the person still and lying down. Avoid moving their head or neck.",
                "If they are unconscious but breathing, place them in the recovery position.",
                "Stop any bleeding with gentle pressure using a clean cloth — but don’t press hard on a possible skull fracture.",
                "Do not remove any objects sticking out of the head.",
                "Watch for signs of serious injury: vomiting, confusion, drowsiness, seizures, or unequal pupils.",
                "Seek immediate medical help if any symptoms worsen or don’t improve."
            )

            instructions.forEachIndexed { index, step ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${index + 1}. ",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFD32F2F)
                        )
                        Text(
                            text = step,
                            fontSize = 15.sp,
                            color = Color.DarkGray
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Warning Note
            Text(
                text = "⚠️ Do not give the person food, drink, or medication. If drowsy, try to keep them awake and responsive until help arrives.",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeadInjuryScreenPreview() {
    HeadInjuryScreen(navController = rememberNavController())
}
