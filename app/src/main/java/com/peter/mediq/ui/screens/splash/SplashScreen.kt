package com.peter.mediq.ui.screens.splash

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

// 🔹 Lottie imports
import com.airbnb.lottie.compose.*
import com.peter.mediq.R

@Composable
fun SplashScreen(navController: NavController) {
    var visible by remember { mutableStateOf(false) }

    // Start animation + move to Login screen
    LaunchedEffect(Unit) {
        visible = true
        delay(5000) // Show splash for 5 sec
        navController.navigate("login") {   // 👈 now goes to login
            popUpTo("splash") { inclusive = true }
        }
    }

    // Gradient background
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF50824), Color(0xFF92979B))
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn() + slideInVertically(initialOffsetY = { 80 }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -80 })
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                // 🔹 Lottie animation ABOVE text
                val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.first_aid_anim))
                val progress by animateLottieCompositionAsState(composition, iterations = LottieConstants.IterateForever)

                LottieAnimation(
                    composition = composition,
                    progress = progress ,
                    modifier = Modifier.size(150.dp)
                )

                Spacer(modifier = Modifier.height(20.dp))



                Spacer(modifier = Modifier.height(20.dp))

                // App name
                Text(
                    text = "MediQ",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(10.dp))

                // Tagline
                Text(
                    text = "Your Health, Our Priority",
                    color = Color.White.copy(alpha = 0.9f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
