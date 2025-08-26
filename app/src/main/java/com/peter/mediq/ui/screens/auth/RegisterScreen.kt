package com.peter.mediq.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.peter.mediq.Navigation.ROUTE_LOGIN
import com.peter.mediq.R
import com.peter.mediq.model.User
import com.peter.mediq.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("user") }
    var expanded by remember { mutableStateOf(false) }

    val roleOptions = listOf("user", "admin")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

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
                    text = "Sign up",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = accentRed
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Username
                OutlinedTextField(
                    value = username, onValueChange = { username = it },
                    label = { Text("User name", color = Color.Gray) },
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

                // Email
                OutlinedTextField(
                    value = email, onValueChange = { email = it },
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

                // Role Dropdown
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    OutlinedTextField(
                        value = role,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Select Role", color = Color.Gray) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
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
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false },
                        modifier = Modifier.background(cardColor)
                    ) {
                        roleOptions.forEach { selectionOption ->
                            DropdownMenuItem(
                                text = { Text(selectionOption, color = darkText) },
                                onClick = {
                                    role = selectionOption
                                    expanded = false
                                },
                                modifier = Modifier.background(cardColor)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Password
                OutlinedTextField(
                    value = password, onValueChange = { password = it },
                    label = { Text("Password", color = Color.Gray) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = darkText),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide),
                                contentDescription = null,
                                tint = accentRed
                            )
                        }
                    },
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

                // Confirm Password
                OutlinedTextField(
                    value = confirmPassword, onValueChange = { confirmPassword = it },
                    label = { Text("Confirm Password", color = Color.Gray) },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    textStyle = MaterialTheme.typography.bodyLarge.copy(color = darkText),
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                painter = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide),
                                contentDescription = null,
                                tint = accentRed
                            )
                        }
                    },
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

                Spacer(modifier = Modifier.height(24.dp))

                // Sign Up Button
                Button(
                    onClick = {
                        // FUNCTIONALITY REMAINS UNCHANGED
                        if (username.isBlank() || email.isBlank() || password.isBlank() || password != confirmPassword) {
                            Toast.makeText(context, "Please fill all fields and ensure passwords match", Toast.LENGTH_SHORT).show()
                        } else {
                            coroutineScope.launch {
                                authViewModel.registerUser(User(username = username, email = email, role = role, password = password))
                                onRegisterSuccess()
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = accentRed),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Sign Up", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Password must have:\n• 8+ characters\n• 1 uppercase, 1 lowercase\n• 1 digit, 1 special character",
                    color = Color.Gray,
                    fontSize = 12.sp,
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Login Button
                TextButton(onClick = { navController.navigate(ROUTE_LOGIN) }) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Login instead", color = darkText)
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = accentRed, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}