package com.coursecampus.planetariumar.data.repository

import com.coursecampus.planetariumar.data.model.Planet
import androidx.compose.ui.graphics.Color

class PlanetRepository {
    
    fun getPlanets(): List<Planet> = listOf(
        Planet(
            id = "sun",
            name = "Sun",
            distanceFromSun = "0 AU",
            orbitTime = "N/A",
            gravity = "274 m/s²",
            surface = "Plasma",
            moons = "0",
            description = "Our star, the Sun, is a massive ball of hydrogen and helium that provides light and energy to our solar system.",
            lottieAnimation = "earth.json",
            color = Color(0xFFFFD700)
        ),
        Planet(
            id = "mercury",
            name = "Mercury",
            distanceFromSun = "0.39 AU",
            orbitTime = "88 Earth days",
            gravity = "3.7 m/s²",
            surface = "Rocky",
            moons = "0",
            description = "Mercury is the smallest and closest planet to the Sun. It has extreme temperature variations.",
            lottieAnimation = "earth.json",
            color = Color(0xFF8C7853)
        ),
        Planet(
            id = "venus",
            name = "Venus",
            distanceFromSun = "0.72 AU",
            orbitTime = "225 Earth days",
            gravity = "8.87 m/s²",
            surface = "Rocky",
            moons = "0",
            description = "Venus is often called Earth's sister planet due to its similar size, but it has a toxic atmosphere.",
            lottieAnimation = "earth.json",
            color = Color(0xFFE6BE8A)
        ),
        Planet(
            id = "earth",
            name = "Earth",
            distanceFromSun = "1 AU",
            orbitTime = "365.25 Earth days",
            gravity = "9.81 m/s²",
            surface = "Rocky",
            moons = "1",
            description = "Our home planet, Earth, is the only known planet with life. It has liquid water and a protective atmosphere.",
            lottieAnimation = "earth.json",
            color = Color(0xFF4B9CD3)
        ),
        Planet(
            id = "mars",
            name = "Mars",
            distanceFromSun = "1.52 AU",
            orbitTime = "687 Earth days",
            gravity = "3.71 m/s²",
            surface = "Rocky",
            moons = "2",
            description = "The Red Planet, Mars, has the largest volcano in the solar system and evidence of ancient water.",
            lottieAnimation = "earth.json",
            color = Color(0xFFCD5C5C)
        ),
        Planet(
            id = "jupiter",
            name = "Jupiter",
            distanceFromSun = "5.20 AU",
            orbitTime = "12 Earth years",
            gravity = "24.79 m/s²",
            surface = "Gas",
            moons = "95",
            description = "Jupiter is the largest planet in our solar system. It's a gas giant with a famous Great Red Spot storm.",
            lottieAnimation = "earth.json",
            color = Color(0xFFDAA520)
        ),
        Planet(
            id = "saturn",
            name = "Saturn",
            distanceFromSun = "9.58 AU",
            orbitTime = "29 Earth years",
            gravity = "10.44 m/s²",
            surface = "Gas",
            moons = "146",
            description = "Saturn is famous for its beautiful ring system made of ice and rock particles.",
            lottieAnimation = "earth.json",
            color = Color(0xFFF4A460)
        ),
        Planet(
            id = "uranus",
            name = "Uranus",
            distanceFromSun = "19.18 AU",
            orbitTime = "84 Earth years",
            gravity = "8.69 m/s²",
            surface = "Ice",
            moons = "27",
            description = "Uranus is an ice giant that rotates on its side. It has a pale blue color due to methane in its atmosphere.",
            lottieAnimation = "earth.json",
            color = Color(0xFF40E0D0)
        ),
        Planet(
            id = "neptune",
            name = "Neptune",
            distanceFromSun = "30.07 AU",
            orbitTime = "165 Earth years",
            gravity = "11.15 m/s²",
            surface = "Ice",
            moons = "16",
            description = "Neptune is the windiest planet with speeds up to 2,100 km/h. It's also the farthest planet from the Sun.",
            lottieAnimation = "earth.json",
            color = Color(0xFF4169E1)
        ),
        Planet(
            id = "pluto",
            name = "Pluto",
            distanceFromSun = "39.48 AU",
            orbitTime = "248 Earth years",
            gravity = "0.62 m/s²",
            surface = "Ice",
            moons = "5",
            description = "Pluto is a dwarf planet in the Kuiper Belt. It was reclassified from a planet in 2006.",
            lottieAnimation = "earth.json",
            color = Color(0xFFC0C0C0)
        )
    )
    
    fun getPlanetById(id: String): Planet? {
        return getPlanets().find { it.id == id }
    }
} 