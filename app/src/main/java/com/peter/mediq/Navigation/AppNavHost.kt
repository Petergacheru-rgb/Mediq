package com.peter.mediq.Navigation


import SettingScreen
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.peter.mediq.data.UserDatabase
import com.peter.mediq.repository.UserRepository
import com.peter.mediq.ui.screens.auth.LoginScreen
import com.peter.mediq.ui.screens.auth.RegisterScreen
import com.peter.mediq.ui.screens.bleeding.BleedingScreen
import com.peter.mediq.ui.screens.burns.BurnsScreen
import com.peter.mediq.ui.screens.call.CallEmergencyScreen
import com.peter.mediq.ui.screens.chocking.ChokingScreen
import com.peter.mediq.ui.screens.cpr.CprScreen
import com.peter.mediq.ui.screens.drowning.DrowningScreen
import com.peter.mediq.ui.screens.electric.ElectricShockScreen
import com.peter.mediq.ui.screens.emergency.EmergencyHelpScreen
import com.peter.mediq.ui.screens.firstaid.* // Import all first aid screens
import com.peter.mediq.ui.screens.fracture.FractureScreen
import com.peter.mediq.ui.screens.head.HeadInjuryScreen
import com.peter.mediq.ui.screens.heat.HeatStrokeScreen
import com.peter.mediq.ui.screens.home.HomeScreen
import com.peter.mediq.ui.screens.hypothermia.HypothermiaScreen
import com.peter.mediq.ui.screens.newposts.AccountScreen
import com.peter.mediq.ui.screens.newposts.HelpScreen
import com.peter.mediq.ui.screens.newposts.PrivacyScreen
import com.peter.mediq.ui.screens.newposts.UploadPostScreen

import com.peter.mediq.ui.screens.seizure.SeizureScreen

import com.peter.mediq.ui.screens.snake.SnakeBiteScreen
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
        composable(ROUTE_EMERGENCYHELP) {
            EmergencyHelpScreen(navController)
        }
        composable(ROUTE_FIRSTAID) {
            FirstAidGuideScreen(navController)
        }
        composable(ROUTE_CALL) {
            CallEmergencyScreen(navController)
        }
        // Define the splash screen
        composable(ROUTE_SPLASH) {
            SplashScreen(navController = navController)
        }
        composable(ROUTE_ACCOUNT) {
            AccountScreen(navController = navController)
        }
        composable(ROUTE_HELP) {
            HelpScreen(navController = navController)
        }
        composable(ROUTE_PRIVACY) {
            PrivacyScreen(navController = navController)
        }

        // MAIN SCREENS
        composable(ROUTE_HOME) { HomeScreen(navController) }
        composable(ROUTE_UPLOAD_POST) { UploadPostScreen(navController) }
        composable(ROUTE_SETTING) {
            SettingScreen(navController)
        }


        // FIRST AID SUB-SCREENS ADDED HERE
        composable(ROUTE_CPR) { CprScreen(onBackClick = { navController.popBackStack() }) }
        composable(ROUTE_BLEEDING) { BleedingScreen(navController) }
        composable(ROUTE_BURNS) { BurnsScreen(navController) }
        composable(ROUTE_CHOKING) { ChokingScreen(navController) }
        composable(ROUTE_ELECTRIC) { ElectricShockScreen(navController) }
        composable(ROUTE_HEAD) { HeadInjuryScreen(navController) }
        composable(ROUTE_FRACTURE) { FractureScreen(navController) }
        composable(ROUTE_SNAKE) { SnakeBiteScreen(navController) }
        composable(ROUTE_DROWNING) { DrowningScreen(navController) }
        composable(ROUTE_SEIZURE) { SeizureScreen(navController) }
        composable(ROUTE_HYPOTHERMIA) { HypothermiaScreen(navController) }
        composable(ROUTE_HEAT) { HeatStrokeScreen(navController) }
    }
}
