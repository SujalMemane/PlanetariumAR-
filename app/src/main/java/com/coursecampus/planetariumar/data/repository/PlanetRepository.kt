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
            description = "Our star, the Sun, is a massive ball of hydrogen and helium that provides light and energy to our solar system. It's so large that about 1.3 million Earths could fit inside it!",
            lottieAnimation = "sun.json",
            color = Color(0xFFFFD700)
        ),
        Planet(
            id = "mercury",
            name = "Mercury",
            distanceFromSun = "0.4 AU",
            orbitTime = "88 Earth days",
            gravity = "3.7 m/s²",
            surface = "Rocky",
            moons = "0",
            description = "Mercury is the smallest and innermost planet in the Solar System. It has no atmosphere and extreme temperature variations from -180°C to 430°C!",
            lottieAnimation = "mercury.json",
            color = Color(0xFF8C7853)
        ),
        Planet(
            id = "venus",
            name = "Venus",
            distanceFromSun = "0.7 AU",
            orbitTime = "225 Earth days",
            gravity = "8.9 m/s²",
            surface = "Rocky",
            moons = "0",
            description = "Venus is often called Earth's 'sister planet' because they're similar in size. It's the hottest planet with thick clouds of sulfuric acid and a runaway greenhouse effect!",
            lottieAnimation = "venus.json",
            color = Color(0xFFE6BE8A)
        ),
        Planet(
            id = "earth", name = "Earth", distanceFromSun = "1 AU", orbitTime = "365 Earth days", gravity = "9.8 m/s²", surface = "Rocky", moons = "1",
            description = "Our home planet, Earth, is the only known planet with life! It has liquid water, a protective atmosphere, and a magnetic field that shields us from harmful solar radiation. Earth's surface is 71% water and 29% land.",
            lottieAnimation = "earth_enhanced.json", color = Color(0xFF4A90E2)
        ),
        Planet(
            id = "mars",
            name = "Mars",
            distanceFromSun = "1.5 AU",
            orbitTime = "687 Earth days",
            gravity = "3.7 m/s²",
            surface = "Rocky",
            moons = "2",
            description = "Mars, the 'Red Planet', has the largest volcano in the solar system (Olympus Mons) and evidence of ancient water. Scientists think it might have had life in the past!",
            lottieAnimation = "mars.json",
            color = Color(0xFFCD5C5C)
        ),
        Planet(
            id = "jupiter",
            name = "Jupiter",
            distanceFromSun = "5.2 AU",
            orbitTime = "12 Earth years",
            gravity = "24.8 m/s²",
            surface = "Gas Giant",
            moons = "95+",
            description = "Jupiter is the largest planet in our solar system! It's a gas giant with a Great Red Spot storm that's been raging for over 400 years. It acts like a cosmic vacuum cleaner protecting Earth from asteroids!",
            lottieAnimation = "jupiter_enhanced.json",
            color = Color(0xFFDAA520)
        ),
        Planet(
            id = "saturn",
            name = "Saturn",
            distanceFromSun = "9.5 AU",
            orbitTime = "29 Earth years",
            gravity = "10.4 m/s²",
            surface = "Gas Giant",
            moons = "146+",
            description = "Saturn is famous for its beautiful rings made of ice and rock particles. It's the least dense planet - if you could put it in water, it would float! The rings are only about 10 meters thick but span thousands of kilometers.",
            lottieAnimation = "saturn_enhanced.json",
            color = Color(0xFFF4A460)
        ),
        Planet(
            id = "uranus",
            name = "Uranus",
            distanceFromSun = "19.2 AU",
            orbitTime = "84 Earth years",
            gravity = "8.7 m/s²",
            surface = "Ice Giant",
            moons = "27",
            description = "Uranus is an ice giant that rotates on its side! It's the coldest planet with temperatures reaching -224°C. It has faint rings and appears blue-green due to methane in its atmosphere.",
            lottieAnimation = "uranus.json",
            color = Color(0xFF40E0D0)
        ),
        Planet(
            id = "neptune",
            name = "Neptune",
            distanceFromSun = "30.1 AU",
            orbitTime = "165 Earth years",
            gravity = "11.2 m/s²",
            surface = "Ice Giant",
            moons = "16",
            description = "Neptune is the windiest planet with storms reaching speeds of 2,100 km/h! It's the farthest planet from the Sun and was discovered through mathematical calculations before it was seen through a telescope.",
            lottieAnimation = "neptune.json",
            color = Color(0xFF4169E1)
        ),
        Planet(
            id = "pluto",
            name = "Pluto",
            distanceFromSun = "39.5 AU",
            orbitTime = "248 Earth years",
            gravity = "0.6 m/s²",
            surface = "Ice",
            moons = "5",
            description = "Pluto is a dwarf planet with a heart-shaped glacier! It's smaller than Earth's moon and has a thin atmosphere. It was reclassified from planet to dwarf planet in 2006, but it's still fascinating!",
            lottieAnimation = "pluto.json",
            color = Color(0xFFC0C0C0)
        )
    )

    fun getPlanetById(id: String): Planet? {
        return getPlanets().find { it.id == id }
    }
} 