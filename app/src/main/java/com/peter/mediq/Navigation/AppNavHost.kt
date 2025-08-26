package com.peter.mediq.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peter.mediq.data.UserDatabase
import com.peter.mediq.repository.UserRepository
import com.peter.mediq.ui.screens.auth.LoginScreen
import com.peter.mediq.ui.screens.auth.RegisterScreen
import com.peter.mediq.ui.screens.home.HomeScreen
import com.peter.mediq.ui.screens.newposts.UploadPostScreen
import com.peter.mediq.ui.screens.splash.SplashScreen
import com.peter.mediq.viewmodel.AuthViewModel

@RequiresApi(Build.VERSION_CODES.Q)
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_SPLASH
) {
    val context = LocalContext.current

    // Initialize DB & Repository once
    val db = UserDatabase.getDatabase(context)
    val repository = UserRepository(db.userDao())

    // Remember AuthViewModel to avoid multiple instances
    val authViewModel = remember { AuthViewModel(repository) }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        // REGISTER SCREEN
        composable(ROUTE_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUTE_LOGIN) {
                    popUpTo(ROUTE_REGISTER) { inclusive = true }
                }
            }
        }

        // LOGIN SCREEN
        composable(ROUTE_LOGIN) {
            LoginScreen(authViewModel, navController)
        }
        // Define the splash screen
        composable(ROUTE_SPLASH) {
            SplashScreen(navController = navController)
        }

        // MAIN SCREENS
        composable(ROUTE_HOME) { HomeScreen(navController) }
        composable(ROUTE_UPLOAD_POST) { UploadPostScreen(navController) }
        // Add other screens here...

    }
}
