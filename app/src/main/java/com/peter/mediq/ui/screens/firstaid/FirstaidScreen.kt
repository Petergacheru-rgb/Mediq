package com.peter.mediq.ui.screens.firstaid

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun FirstAidGuideScreen(navController: NavController) {
    val scrollState = rememberScrollState()

    // Gradient background
    val gradientBackground = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEEF7FF), // Soft light blue
            Color(0xFFF9FBFD), // Almost white
            Color(0xFFE3F2FD)  // Pale sky blue
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(gradientBackground)
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        // Heading
        Text(
            text = "🚑 First Aid Guide",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Text(
            text = "Quick lifesaving tips — not a replacement for professional care. Always call emergency services if needed.",
            fontSize = 16.sp,
            color = Color.DarkGray,
            lineHeight = 22.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        // Sections
        SectionCard(
            title = "Basic First Aid Principles",
            color = Color(0xFFFFF176) // Light Yellow
        ) {
            InfoPoint("😌", "Stay calm and ensure your safety first.")
            InfoPoint("📞", "Call emergency services immediately.")
            InfoPoint("👀", "Assess the situation and the injured person.")
            InfoPoint("🧤", "Use protective gear like gloves if possible.")
        }

        SectionCard(
            title = "Kenya Emergency Numbers",
            color = Color(0xFF81D4FA) // Sky Blue
        ) {
            InfoPoint("🚑", "Ambulance: 999 or 112")
            InfoPoint("🔥", "Fire: 999")
            InfoPoint("👮", "Police: 999")
        }

        SectionCard(
            title = "Quick Response Tips",
            color = Color(0xFFA5D6A7) // Soft Green
        ) {
            InfoPoint("❤️", "CPR: Push hard & fast in the chest center.")
            InfoPoint("🩹", "Bleeding: Apply firm pressure with a clean cloth.")
            InfoPoint("💧", "Burns: Cool with running water 10+ minutes.")
            InfoPoint("⚠️", "Choking: Perform abdominal thrusts if trained.")
            InfoPoint("🦴", "Fractures: Immobilize the injured part.")
        }

        SectionCard(
            title = "Prevention Tips",
            color = Color(0xFFFFAB91) // Peach
        ) {
            InfoPoint("🧰", "Keep a stocked first aid kit at home & work.")
            InfoPoint("🚗", "Follow road safety rules.")
            InfoPoint("🚸", "Don’t leave children unattended near water.")
        }

        Spacer(modifier = Modifier.height(28.dp))

        // Disclaimer
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.15f)
            ),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Text(
                text = "⚠️ Disclaimer: Informational only. Seek professional help whenever possible.",
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                color = MaterialTheme.colorScheme.error,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun SectionCard(
    title: String,
    color: Color,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
        colors = CardDefaults.cardColors(
            containerColor = color.copy(alpha = 0.18f)
        ),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = color,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            content()
        }
    }
}

@Composable
fun InfoPoint(emoji: String, text: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = emoji,
            fontSize = 20.sp,
            modifier = Modifier.width(28.dp)
        )
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.DarkGray,
            lineHeight = 20.sp
        )
    }
}
