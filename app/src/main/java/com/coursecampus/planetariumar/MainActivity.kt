package com.coursecampus.planetariumar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.core.view.WindowCompat
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.coursecampus.planetariumar.navigation.Screen
import com.coursecampus.planetariumar.ui.home.HomeScreen
import com.coursecampus.planetariumar.ui.planetinfo.PlanetInfoSheet
import com.coursecampus.planetariumar.ui.splash.SplashScreen
import com.coursecampus.planetariumar.ui.theme.PlanetariumARTheme
import com.coursecampus.planetariumar.ui.theme.SpaceBlack

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Make the app edge-to-edge (behind system bars)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        
        setContent {
            PlanetariumARTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black), // Ensure black background extends everywhere
                    color = Color.Transparent // Make surface transparent so our custom backgrounds show
                ) {
                    PlanetariumARApp()
                }
            }
        }
    }
}

@Composable
fun PlanetariumARApp() {
    val navController = rememberNavController()
    
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route,
        modifier = Modifier.fillMaxSize() // Ensure NavHost fills entire screen
    ) {
        composable(Screen.Splash.route) {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
    )
}

        composable(Screen.Home.route) {
            HomeScreen(
                onPlanetClick = { planetId ->
                    navController.navigate(Screen.PlanetInfo.createRoute(planetId))
                }
            )
        }
        
        composable(
            route = Screen.PlanetInfo.route,
            arguments = listOf(
                navArgument("planetId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val planetId = backStackEntry.arguments?.getString("planetId") ?: ""
            PlanetInfoSheet(
                planetId = planetId,
                onDismiss = {
                    navController.popBackStack()
                }
            )
        }
    }
}