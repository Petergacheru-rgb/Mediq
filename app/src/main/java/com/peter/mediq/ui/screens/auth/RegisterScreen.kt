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
    var role by remember { mutableStateOf("user") }  // ✅ only once
    var expanded by remember { mutableStateOf(false) }

    val roleOptions = listOf("user", "admin")
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope() // ✅ for suspend calls

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF141E30), Color(0xFF243B55))
                )
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .width(360.dp)
                .background(Color(0xFF1C1C1E), RoundedCornerShape(20.dp))
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Sign up", fontSize = 30.sp, fontWeight = FontWeight.Bold, color = Color(0xFFFF5C5C))

            Spacer(modifier = Modifier.height(24.dp))

            // Username
            OutlinedTextField(
                value = username, onValueChange = { username = it },
                label = { Text("User name") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5C5C),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = Color(0xFF2C2C2E),
                    unfocusedContainerColor = Color(0xFF2C2C2E),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Email
            OutlinedTextField(
                value = email, onValueChange = { email = it },
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5C5C),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = Color(0xFF2C2C2E),
                    unfocusedContainerColor = Color(0xFF2C2C2E),
                    cursorColor = Color.White
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
                    label = { Text("Select Role") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    roleOptions.forEach { selectionOption ->
                        DropdownMenuItem(
                            text = { Text(selectionOption) },
                            onClick = {
                                role = selectionOption
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Password
            OutlinedTextField(
                value = password, onValueChange = { password = it },
                label = { Text("Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide),
                            contentDescription = null,
                            tint = Color(0xFFFF5C5C)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5C5C),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = Color(0xFF2C2C2E),
                    unfocusedContainerColor = Color(0xFF2C2C2E),
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Confirm Password
            OutlinedTextField(
                value = confirmPassword, onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(R.drawable.passwordshow) else painterResource(R.drawable.passwordhide),
                            contentDescription = null,
                            tint = Color(0xFFFF5C5C)
                        )
                    }
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color(0xFFFF5C5C),
                    unfocusedBorderColor = Color.Gray,
                    focusedContainerColor = Color(0xFF2C2C2E),  // ✅ fixed
                    unfocusedContainerColor = Color(0xFF2C2C2E), // ✅ fixed
                    cursorColor = Color.White
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Sign Up Button
            Button(
                onClick = {
                    if (username.isBlank() || email.isBlank() || password.isBlank() || password != confirmPassword) {
                        Toast.makeText(context, "Please fill all fields and ensure passwords match", Toast.LENGTH_SHORT).show()
                    } else {
                        coroutineScope.launch { // ✅ save properly
                            authViewModel.registerUser(User(username = username, email = email, role = role, password = password))
                            onRegisterSuccess()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth().height(52.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5C5C)),
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
                    Text("Login instead", color = Color(0xFFFF5C5C))
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(Icons.Filled.ArrowForward, contentDescription = null, tint = Color(0xFFFF5C5C), modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}
