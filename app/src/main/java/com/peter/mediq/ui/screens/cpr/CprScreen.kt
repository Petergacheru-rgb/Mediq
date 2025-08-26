package com.peter.mediq.ui.screens.cpr

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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peter.mediq.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CprScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "CPR Instructions",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1565C0) // deep blue for emergency guidance
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .background(Color(0xFFF4F7FB)) // soft light background
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // CPR Illustration
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_cpr),
                    contentDescription = "CPR Illustration",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Follow these steps carefully:",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF0D47A1), // darker blue for emphasis
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CPRStep(1, "Check if the person is responsive. Tap and shout.")
            CPRStep(2, "Call emergency services immediately or ask someone nearby.")
            CPRStep(3, "Lay the person flat on their back on a firm surface.")
            CPRStep(4, "Start chest compressions – 30 compressions at a rate of 100–120 per minute.")
            CPRStep(5, "Give 2 rescue breaths if trained. Tilt the head, lift the chin, pinch the nose.")
            CPRStep(6, "Repeat 30 compressions and 2 breaths until help arrives.")

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Note: Only perform rescue breaths if you’re trained. Otherwise, continue with compressions only.",
                fontSize = 14.sp,
                color = Color.Gray,
                fontWeight = androidx.compose.ui.text.font.FontWeight.Medium,
                modifier = Modifier.align(Alignment.Start)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCprScreen() {
    CprScreen(onBackClick = {})
}

@Composable
fun CPRStep(number: Int, instruction: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(14.dp)) {
            Text(
                text = "$number.",
                fontWeight = androidx.compose.ui.text.font.FontWeight.Bold,
                fontSize = 16.sp,
                color = Color(0xFF1565C0), // match app bar theme
                modifier = Modifier.padding(end = 8.dp)
            )
            Text(
                text = instruction,
                fontSize = 15.sp,
                color = Color.Black
            )
        }
    }
}
