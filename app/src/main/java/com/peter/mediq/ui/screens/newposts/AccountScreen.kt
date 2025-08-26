package com.peter.mediq.ui.screens.newposts

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

// The main composable function for the Account screen.
@Composable
fun AccountScreen(navController: NavController) {
    // State variables to hold the user's name and email.
    // The remember keyword ensures the state is preserved across recompositions.
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Account Settings",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Text field for the user's name.
        // The value and onValueChange callbacks are essential for state management.
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Text field for the user's email.
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        // Button to save changes.
        Button(
            onClick = {
                // In a real app, you would perform an action like saving to a database.
                // For this example, we'll print the data to the console.
                println("Saving changes: Name=$name, Email=$email")
            },
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(top = 16.dp)
        ) {
            Text("Save Changes")
        }
    }
}

// A preview composable to see the screen's UI in Android Studio's preview window.
@Preview(showBackground = true)
@Composable
fun PreviewAccountScreen() {
    AccountScreen(navController = rememberNavController())
}
