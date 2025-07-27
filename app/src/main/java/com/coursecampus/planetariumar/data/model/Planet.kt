package com.coursecampus.planetariumar.data.model

data class Planet(
    val id: String,
    val name: String,
    val distanceFromSun: String,
    val orbitTime: String,
    val gravity: String,
    val surface: String,
    val moons: String,
    val description: String,
    val lottieAnimation: String,
    val color: androidx.compose.ui.graphics.Color
) 