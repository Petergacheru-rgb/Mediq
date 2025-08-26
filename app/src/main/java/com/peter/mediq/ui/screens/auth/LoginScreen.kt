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
import androidx.compose.ui.graphics.Brush
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFFF2F3F5), Color(0xFFE51708))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF565252)),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Welcome Back 👋",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Login to continue",
                    fontSize = 14.sp,
                    color = Color(0xFF8D5A5A)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Email Field
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Password Field
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(R.drawable.passwordshow)
                                else painterResource(R.drawable.passwordhide),
                                contentDescription = if (passwordVisible) "Hide Password" else "Show Password",
                                tint = Color(0xFFD00C0C)
                            )
                        }
                    }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Login Button
                Button(
                    onClick = {
                        if (email.isBlank() || password.isBlank()) {
                            Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                        } else {
                            authViewModel.loginUser(email, password)
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE7190A)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Login", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Navigate to Register
                TextButton(onClick = { navController.navigate(ROUTE_REGISTER) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Don’t have an account?", color = Color.White)
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            Icons.Filled.ArrowForward,
                            contentDescription = "Sign up",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}
