import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// Define routes for navigation
sealed class Screen(val route: String) {
    object SettingsList : Screen("settings_list")
    object Account : Screen("account")
    object Notifications : Screen("notifications")
    object Privacy : Screen("privacy")
    object Help : Screen("help")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚙️ Settings") },
                actions = {
                    IconButton(onClick = {
                        // This is where you would place your refresh logic
                        println("Refreshing settings...")
                    }) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = "Refresh"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SettingItem(
                title = "Account",
                description = "Manage your account details",
                onClick = { navController.navigate(Screen.Account.route) }
            )

            SettingItem(
                title = "Privacy",
                description = "Privacy and security options",
                onClick = { navController.navigate(Screen.Privacy.route) }
            )
            SettingItem(
                title = "Help",
                description = "Get support and FAQs",
                onClick = { navController.navigate(Screen.Help.route) }
            )
        }
    }
}

@Composable
fun SettingItem(title: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigableScreenTemplate(
    title: String,
    navController: NavController,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            content()
        }
    }
}

@Composable
fun AccountScreen(navController: NavController) {
    var name by remember { mutableStateOf("John Doe") }
    var email by remember { mutableStateOf("john.doe@example.com") }

    NavigableScreenTemplate(title = "Account Settings", navController = navController) {
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )
        Button(
            onClick = { println("Saving changes: Name=$name, Email=$email") },
            modifier = Modifier.fillMaxWidth().height(48.dp).padding(top = 16.dp)
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
fun HelpScreen(navController: NavController) {
    NavigableScreenTemplate(title = "Help & Support", navController = navController) {
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Q: How do I reset my password?")
                Text(text = "A: You can reset your password from the Account settings screen.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Q: How do I change my notification preferences?")
                Text(text = "A: Go to the Notifications settings screen to manage your preferences.")
            }
        }
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Contact Us",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Email: support@example.com")
                Text(text = "Phone: (555) 123-4567")
            }
        }
    }
}

@Composable
fun PrivacyScreen(navController: NavController) {
    NavigableScreenTemplate(title = "Privacy & Security", navController = navController) {
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Data Usage",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "We use your data to improve our services and personalize your experience. Your information is never sold to third parties.")
            }
        }
        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Connected Services",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(text = "Manage which third-party services are connected to your account.")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "No connected services found.")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainSettingsApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.SettingsList.route) {
        composable(Screen.SettingsList.route) { SettingScreen(navController = navController) }
        composable(Screen.Account.route) { AccountScreen(navController = navController) }
        composable(Screen.Notifications.route) { NavigableScreenTemplate(title = "Notification Settings", navController = navController) { Text("Notification settings content goes here.") } }
        composable(Screen.Privacy.route) { PrivacyScreen(navController = navController) }
        composable(Screen.Help.route) { HelpScreen(navController = navController) }
    }
}
