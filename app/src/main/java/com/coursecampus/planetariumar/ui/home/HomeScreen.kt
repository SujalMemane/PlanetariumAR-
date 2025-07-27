package com.coursecampus.planetariumar.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.coursecampus.planetariumar.R
import com.coursecampus.planetariumar.data.model.Planet
import com.coursecampus.planetariumar.data.repository.PlanetRepository
import com.coursecampus.planetariumar.ui.theme.DeepSpace
import com.coursecampus.planetariumar.ui.theme.NeonBlue
import com.coursecampus.planetariumar.ui.theme.NeonGreen
import com.coursecampus.planetariumar.ui.theme.SpaceBlack
import com.coursecampus.planetariumar.ui.theme.StarWhite
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HomeScreen(
    onPlanetClick: (String) -> Unit
) {
    val planets = remember { PlanetRepository().getPlanets() }
    val listState = rememberLazyListState()
    var lastClickedPlanetIndex by remember { mutableStateOf(0) }
    var isFirstLoad by remember { mutableStateOf(true) }
    
    // Scroll to the last clicked planet when returning from bottom sheet
    LaunchedEffect(lastClickedPlanetIndex) {
        if (!isFirstLoad) {
            // Add some delay to ensure proper layout
            kotlinx.coroutines.delay(100)
            // Scroll to the last clicked planet
            listState.animateScrollToItem(lastClickedPlanetIndex, scrollOffset = 0)
        }
    }
    
    // Mark first load as complete after initial setup
    LaunchedEffect(Unit) {
        kotlinx.coroutines.delay(200)
        isFirstLoad = false
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Pure black background first
    ) {
        // Full-screen meteors background - same as splash screen
        val skyMeteorsComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.sky_meteors)
        )
        val skyMeteorsProgress by animateLottieCompositionAsState(
            composition = skyMeteorsComposition,
            isPlaying = true,
            speed = 0.9f, // Slower speed for more elegant animation
            iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
        )
        
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize() // Fill entire screen
        )
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Enhanced Header with better typography
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 50.dp, start = 24.dp, end = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Our Solar System",
                    style = androidx.compose.material3.MaterialTheme.typography.displayMedium,
                    color = NeonBlue,
                    textAlign = TextAlign.Center
                )
                
                Spacer(modifier = Modifier.height(12.dp))
                
                Text(
                    text = "Explore the planets and their orbits",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    color = StarWhite.copy(alpha = 0.9f),
                    textAlign = TextAlign.Center
                )
            }
            
            // Horizontal Solar System - taking remaining space
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Take remaining space
                    .padding(vertical = 30.dp),
                contentAlignment = Alignment.Center // Center vertically
            ) {
                LazyRow(
                    state = listState,
                    horizontalArrangement = Arrangement.spacedBy(0.dp), // No fixed spacing, we'll use custom spacing
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(
                        start = 0.dp, // Extra padding to center sun
                        end = 50.dp
                    ),
                    modifier = Modifier.fillMaxSize(), // Take MAX SIZE
                    verticalAlignment = Alignment.CenterVertically // Center planets vertically
                ) {
                    items(planets) { planet ->
                        val planetIndex = planets.indexOf(planet)
                        PlanetItem(
                            planet = planet,
                            onClick = { 
                                lastClickedPlanetIndex = planetIndex
                                onPlanetClick(planet.id) 
                            },
                            isFirst = planet.name.lowercase() == "sun"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
    onClick: () -> Unit,
    isFirst: Boolean
) {
    var isPressed by remember { mutableStateOf(false) }
    var shouldNavigate by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.85f else 1f,
        animationSpec = tween(200),
        label = "planet scale"
    )
    
    val elevation by animateFloatAsState(
        targetValue = if (isPressed) 0f else 8f,
        animationSpec = tween(200),
        label = "planet elevation"
    )
    
    // Handle navigation after animation
    LaunchedEffect(shouldNavigate) {
        if (shouldNavigate) {
            kotlinx.coroutines.delay(200) // Slightly longer delay for better feedback
            onClick()
            shouldNavigate = false
        }
    }
    
    // Lottie composition for planet animation
    val planetComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            when (planet.name.lowercase()) {
                "sun" -> R.raw.sun
                "mercury" -> R.raw.mercury
                "venus" -> R.raw.mercury
                "earth" -> R.raw.earth
                "mars" -> R.raw.mercury
                "jupiter" -> R.raw.mercury
                "saturn" -> R.raw.mercury
                "uranus" -> R.raw.mercury
                "neptune" -> R.raw.mercury
                "pluto" -> R.raw.mercury
                else -> R.raw.mercury
            }
        )
    )
    val planetProgress by animateLottieCompositionAsState(
        composition = planetComposition,
        isPlaying = true,
        speed = 0.9f, // Slower speed for more elegant animation
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    val planetSize = getPlanetSize(planet.name)
    val isSun = planet.name.lowercase() == "sun"
    
    // Calculate proportional spacing based on real distances
    val spacingBefore = getProportionalSpacing(planet.name)
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(start = if (isFirst) 0.dp else spacingBefore)
            .clickable {
                isPressed = true
                shouldNavigate = true
            }
    ) {
        // Planet with enhanced visual feedback
        Box(
            modifier = Modifier
                .size(planetSize)
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            // Planet animation
            LottieAnimation(
                composition = planetComposition,
                progress = { planetProgress },
                modifier = Modifier.size(planetSize)
            )
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Enhanced planet name with better typography
        Text(
            text = planet.name,
            style = if (isSun) 
                androidx.compose.material3.MaterialTheme.typography.headlineMedium
            else 
                androidx.compose.material3.MaterialTheme.typography.titleLarge,
            color = StarWhite,
            textAlign = TextAlign.Center,
            fontWeight = if (isSun) FontWeight.Bold else FontWeight.SemiBold
        )
        
        Spacer(modifier = Modifier.height(12.dp))
        
        // Enhanced distance display with better typography
        Text(
            text = planet.distanceFromSun,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = NeonGreen,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun getPlanetSize(planetName: String): androidx.compose.ui.unit.Dp {
    return when (planetName.lowercase()) {
        "sun" -> 500.dp // Much larger sun
        "jupiter" -> 380.dp
        "saturn" -> 340.dp
        "uranus" -> 280.dp
        "neptune" -> 270.dp
        "earth" -> 220.dp
        "venus" -> 210.dp
        "mars" -> 200.dp
        "mercury" -> 180.dp
        "pluto" -> 160.dp
        else -> 220.dp
    }
}

// Function to calculate proportional spacing based on real distances
private fun getProportionalSpacing(planetName: String): androidx.compose.ui.unit.Dp {
    return when (planetName.lowercase()) {
        "sun" -> 0.dp // No spacing before sun
        "mercury" -> 20.dp // Small spacing from sun
        "venus" -> 30.dp // Slightly more spacing
        "earth" -> 40.dp // More spacing
        "mars" -> 50.dp // Even more spacing
        "jupiter" -> 120.dp // Much more spacing (far from inner planets)
        "saturn" -> 80.dp // Large spacing
        "uranus" -> 100.dp // Very large spacing
        "neptune" -> 120.dp // Maximum spacing
        "pluto" -> 150.dp // Extreme spacing (very far)
        else -> 50.dp
    }
}

@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onPlanetClick = {}
    )
} 