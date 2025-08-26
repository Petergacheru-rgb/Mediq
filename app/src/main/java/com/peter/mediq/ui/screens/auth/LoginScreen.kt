package com.peter.mediq.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peter.mediq.Navigation.ROUTE_HOME
import com.peter.mediq.Navigation.ROUTE_LOGIN
import com.peter.mediq.Navigation.ROUTE_REGISTER
import com.peter.mediq.Navigation.ROUTE_UPLOAD_POST
import com.peter.mediq.R
import com.peter.mediq.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    authViewModel: AuthViewModel,
    navController: NavController
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    val context = LocalContext.current

    // Observe StateFlow from ViewModel
    val loggedInUser by authViewModel.loggedInUser.collectAsState()

    // Navigate when login succeeds
    LaunchedEffect(loggedInUser) {
        loggedInUser?.let { user ->
            if (user.role == "admin") {
                navController.navigate(ROUTE_UPLOAD_POST) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            } else {
                navController.navigate(ROUTE_HOME) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            }
        }
    }

    // --- Start of new UI and color scheme for a first-aid app ---
    val primaryLight = Color(0xFFF0F2F5) // Very light gray for background
    val accentRed = Color(0xFFE7190A) // Clean, iconic red for first-aid
    val cardColor = Color.White // Pure white for a clean look
    val darkText = Color(0xFF333333) // Dark gray for high-contrast text

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(primaryLight)
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .width(360.dp)
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentRed
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to continue",
                    fontSize = 16.sp,
                    color = darkText.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email", color = Color.Gray) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = darkText),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentRed,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = accentRed,
                        focusedLabelColor = accentRed,
                        unfocusedLabelColor = Color.Gray
                    )
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password", color = Color.Gray) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = darkText),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = accentRed,
                        unfocusedBorderColor = Color.Gray,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        cursorColor = accentRed,
                        focusedLabelColor = accentRed,
                        unfocusedLabelColor = Color.Gray
                    ),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(R.drawable.passwordshow)
                                else painterResource(R.drawable.passwordhide),
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                tint = accentRed
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = {
                        // FUNCTIONALITY REMAINS UNCHANGED
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                        } else {
                            authViewModel.loginUser(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentRed),
                    shape = RoundedCornerShape(12.dp),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text("Login", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigate to Register
                TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("\"New to Mediq?\"", color = darkText)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = "Sign up",
                            tint = accentRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}