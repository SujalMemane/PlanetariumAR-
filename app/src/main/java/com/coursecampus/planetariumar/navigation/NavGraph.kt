package com.coursecampus.planetariumar.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object PlanetInfo : Screen("planet_info/{planetId}") {
        fun createRoute(planetId: String) = "planet_info/$planetId"
    }
} 