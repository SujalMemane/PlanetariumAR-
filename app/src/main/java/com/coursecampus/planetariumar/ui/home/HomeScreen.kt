package com.coursecampus.planetariumar.ui.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun HomeScreen(
    onPlanetClick: (String) -> Unit
) {
    val planets = remember { PlanetRepository().getPlanets() }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Solid black background first
            .background(
                Brush.verticalGradient(
                    colors = listOf(SpaceBlack, DeepSpace)
                )
            )
    ) {
        // Full-screen meteors background - covering entire screen with no padding
        val skyMeteorsComposition by rememberLottieComposition(
            LottieCompositionSpec.RawRes(R.raw.sky_meteors)
        )
        val skyMeteorsProgress by animateLottieCompositionAsState(
            composition = skyMeteorsComposition,
            isPlaying = true,
            iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
        )
        
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize()
        )
        
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Enhanced header with better design - no emojis
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DeepSpace.copy(alpha = 0.8f)
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 8.dp
                )
            ) {
                Text(
                    text = "Our Solar System",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = NeonBlue,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            }
            
            // Horizontal Solar System - taking MAX HEIGHT and centered
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f) // Take ALL remaining space
                    .padding(vertical = 10.dp), // Reduced padding to maximize height
                contentAlignment = Alignment.Center // Center vertically
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(40.dp), // More spacing for larger planets
                    contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 40.dp), // More padding for larger planets
                    modifier = Modifier.fillMaxSize(), // Take MAX SIZE
                    verticalAlignment = Alignment.CenterVertically // Center planets vertically
                ) {
                    items(planets) { planet ->
                        PlanetItem(
                            planet = planet,
                            onClick = { onPlanetClick(planet.id) }
                        )
                    }
                }
            }
            
            // Enhanced instructions card with better design - no emojis
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = DeepSpace.copy(alpha = 0.9f)
                ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 12.dp
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Tap on any planet to learn more",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = NeonGreen,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "Scroll to explore the entire solar system",
                        fontSize = 16.sp,
                        color = StarWhite,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Composable
fun PlanetItem(
    planet: Planet,
    onClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,
        animationSpec = tween(100),
        label = "planet scale"
    )
    
    // Lottie composition for planet animation
    val planetComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.earth)
    )
    val planetProgress by animateLottieCompositionAsState(
        composition = planetComposition,
        isPlaying = true,
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    val planetSize = getPlanetSize(planet.name)
    val isSun = planet.name.lowercase() == "sun"
    
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                isPressed = true
                onClick()
            }
    ) {
        // Planet with only animation - no background, much larger
        Box(
            modifier = Modifier
                .size(planetSize)
                .scale(scale),
            contentAlignment = Alignment.Center
        ) {
            LottieAnimation(
                composition = planetComposition,
                progress = { planetProgress },
                modifier = Modifier.size(planetSize)
            )
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Enhanced planet name with better styling
        Text(
            text = planet.name,
            fontSize = if (isSun) 26.sp else 22.sp,
            fontWeight = FontWeight.Bold,
            color = StarWhite,
            textAlign = TextAlign.Center
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Enhanced distance display
        Text(
            text = planet.distanceFromSun,
            fontSize = 16.sp,
            color = NeonGreen,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun getPlanetSize(planetName: String): androidx.compose.ui.unit.Dp {
    return when (planetName.lowercase()) {
        "sun" -> 450.dp // Even larger for max height
        "jupiter" -> 320.dp
        "saturn" -> 280.dp
        "uranus" -> 220.dp
        "neptune" -> 210.dp
        "earth" -> 180.dp
        "venus" -> 170.dp
        "mars" -> 160.dp
        "mercury" -> 140.dp
        "pluto" -> 120.dp
        else -> 180.dp
    }
}

@Composable
fun HomeScreenPreview() {
    HomeScreen(
        onPlanetClick = {}
    )
} 