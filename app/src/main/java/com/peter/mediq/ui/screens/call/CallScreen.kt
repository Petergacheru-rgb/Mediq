package com.peter.mediq.ui.screens.call

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun CallEmergencyScreen(navController: NavController) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    // Gradient background for eye-catching look
    val gradient = Brush.verticalGradient(
        colors = listOf(Color(0xFFBBDEFB), Color(0xFFE3F2FD)) // soft blue shades
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        // Header card
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Call,
                    contentDescription = "Emergency Call",
                    tint = Color(0xFFD32F2F),
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Call Emergency",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFD32F2F)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Tap a button to call emergency services.\nThe dialer will open with the number pre-filled.",
                    fontSize = 15.sp,
                    color = Color.Gray,
                    lineHeight = 20.sp,
                    modifier = Modifier.padding(horizontal = 8.dp),
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center
                )
            }
        }

        Spacer(modifier = Modifier.height(25.dp))

        // Emergency numbers
        CallButton("🚑 Ambulance", "999", context, Color(0xFF81D4FA))
        CallButton("👮 Police", "999", context, Color(0xFFA5D6A7))
        CallButton("🔥 Fire Brigade", "999", context, Color(0xFFFFAB91))
        CallButton("📱 Mobile Emergency", "112", context, Color(0xFFFFF176))
        CallButton("🚑 Red Cross Kenya", "0700395395", context, Color(0xFFCE93D8))
        CallButton("🚨 St John Ambulance", "0721611311", context, Color(0xFFFFCC80))

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Composable
fun CallButton(
    label: String,
    phoneNumber: String,
    context: android.content.Context,
    color: Color
) {
    Button(
        onClick = {
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            context.startActivity(intent)
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(60.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Text(
            text = "$label ($phoneNumber)",
            fontSize = 18.sp,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold
        )
    }
}
