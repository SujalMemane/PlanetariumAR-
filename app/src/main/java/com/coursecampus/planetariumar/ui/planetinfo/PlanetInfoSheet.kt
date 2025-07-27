package com.coursecampus.planetariumar.ui.planetinfo

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.coursecampus.planetariumar.R
import com.coursecampus.planetariumar.data.model.Planet
import com.coursecampus.planetariumar.data.repository.PlanetRepository
import com.coursecampus.planetariumar.ui.theme.DeepSpace
import com.coursecampus.planetariumar.ui.theme.EarthBlue
import com.coursecampus.planetariumar.ui.theme.MeteorGray
import com.coursecampus.planetariumar.ui.theme.NeonBlue
import com.coursecampus.planetariumar.ui.theme.NeonGreen
import com.coursecampus.planetariumar.ui.theme.SpaceBlack
import com.coursecampus.planetariumar.ui.theme.StarWhite
import com.coursecampus.planetariumar.ui.theme.StellarPurple
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.offset

@Composable
fun PlanetInfoSheet(
    planetId: String,
    onDismiss: () -> Unit
) {
    val planet = remember { PlanetRepository().getPlanetById(planetId) }
    
    if (planet != null) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        ) {
            AnimatedPlanetBottomSheet(
                planet = planet,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun AnimatedPlanetBottomSheet(
    planet: Planet,
    onDismiss: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "content scale"
    )
    
    // Planet animation states
    val planetScale by animateFloatAsState(
        targetValue = if (isPressed) 1.2f else 1f,
        animationSpec = tween(300),
        label = "planet scale"
    )
    
    val planetOffset by animateFloatAsState(
        targetValue = if (isPressed) -100f else 0f,
        animationSpec = tween(300),
        label = "planet offset"
    )
    
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
    
    // Full-screen meteors background - covering entire screen
    val skyMeteorsComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sky_meteors)
    )
    val skyMeteorsProgress by animateLottieCompositionAsState(
        composition = skyMeteorsComposition,
        isPlaying = true,
        speed = 0.9f, // Slower speed for more elegant animation
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Pure black background
    ) {
        // Full-screen meteors background - NO PADDING, FILLS ENTIRE SCREEN
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize() // Fill entire screen
        )
        
        // Animated planet floating at the TOP of the screen
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 50.dp)
                .offset(y = planetOffset.dp)
                .scale(planetScale)
        ) {
            LottieAnimation(
                composition = planetComposition,
                progress = { planetProgress },
                modifier = Modifier.size(260.dp) // Large floating planet at top
            )
        }
        
        // Beautiful Bottom Sheet with planet info - ENHANCED DESIGN
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .heightIn(max = 580.dp) // Slightly larger for more content
                .padding(vertical = 6.dp) // Only vertical padding, no horizontal padding
                .scale(scale)
                .clickable { isPressed = true },
            shape = RoundedCornerShape(topStart = 28.dp, topEnd = 28.dp), // Larger radius
            colors = CardDefaults.cardColors(
                containerColor = planet.color.copy(alpha = 0.15f) // Use planet's color with transparency
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 28.dp // Higher elevation
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp) // Slightly more padding for content
            ) {
                // Sheet handle - ENHANCED
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 14.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 56.dp, height = 5.dp) // Larger handle
                            .background(
                                color = MeteorGray.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(2.5.dp)
                            )
                    )
                }
                
                // Header with close button - FIXED AT TOP
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = planet.name,
                            style = androidx.compose.material3.MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = planet.color // Use planet's color for title
                        )
                        Text(
                            text = getPlanetType(planet.name),
                            style = androidx.compose.material3.MaterialTheme.typography.labelLarge,
                            color = planet.color.copy(alpha = 0.8f), // Use planet's color with transparency
                            fontWeight = FontWeight.Medium
                        )
                    }
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(56.dp) // Larger button
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = planet.color.copy(alpha = 0.9f), // Use planet's color for close button
                            modifier = Modifier.size(32.dp) // Larger icon
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Scrollable content - DESCRIPTION AND FACTS
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Description - ENHANCED
                    Text(
                        text = planet.description,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                        color = StarWhite,
                        textAlign = TextAlign.Center,
                        lineHeight = 28.sp, // Better line height
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    // Enhanced planet facts with more information
                    PlanetFactsSection(planet)
                    
                    Spacer(modifier = Modifier.height(20.dp))
                    
                    // Fun fact section
                    FunFactSection(planet)
                    
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}

@Composable
fun PlanetFactsSection(planet: Planet) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Quick Facts",
            style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = planet.color.copy(alpha = 0.9f), // Use planet's color
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 20.dp)
        )
        
        // Facts without cards - ENHANCED LAYOUT
        FactRow("Distance from Sun", planet.distanceFromSun, planet.color)
        FactRow("Orbit Time", planet.orbitTime, planet.color)
        FactRow("Gravity", planet.gravity, planet.color)
        FactRow("Surface Type", planet.surface, planet.color)
        FactRow("Number of Moons", planet.moons, planet.color)
    }
}

@Composable
fun FunFactSection(planet: Planet) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Did You Know?",
            style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = planet.color.copy(alpha = 0.8f), // Use planet's color
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 18.dp)
        )
        
        Text(
            text = getFunFact(planet.name),
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = StarWhite.copy(alpha = 0.9f),
            textAlign = TextAlign.Center,
            lineHeight = 24.sp,
            modifier = Modifier.padding(vertical = 10.dp)
        )
    }
}

@Composable
fun FactRow(
    label: String,
    value: String,
    planetColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), // More spacing
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = planetColor.copy(alpha = 0.7f), // Use planet's color with transparency
            fontWeight = FontWeight.Medium
        )
        
        Text(
            text = value,
            style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
            color = StarWhite,
            fontWeight = FontWeight.Bold
        )
    }
    
    // Simple divider between facts
    if (label != "Number of Moons") { // Don't show divider after last fact
        Divider(
            modifier = Modifier.padding(vertical = 8.dp),
            color = planetColor.copy(alpha = 0.3f), // Use planet's color for divider
            thickness = 1.dp
        )
    }
}

// Helper functions for additional content
private fun getPlanetType(planetName: String): String {
    return when (planetName.lowercase()) {
        "sun" -> "Yellow Dwarf Star"
        "mercury", "venus", "earth", "mars" -> "Terrestrial Planet"
        "jupiter", "saturn" -> "Gas Giant"
        "uranus", "neptune" -> "Ice Giant"
        "pluto" -> "Dwarf Planet"
        else -> "Celestial Body"
    }
}

private fun getFunFact(planetName: String): String {
    return when (planetName.lowercase()) {
        "sun" -> "The Sun contains 99.86% of the solar system's mass! It's so bright that you can't look at it directly without special protection."
        "mercury" -> "A day on Mercury is longer than its year! It takes 176 Earth days to rotate once, but only 88 days to orbit the Sun."
        "venus" -> "Venus rotates backwards compared to most planets! It spins clockwise while most planets spin counterclockwise."
        "earth" -> "Earth is the only planet not named after a Greek or Roman god! The name 'Earth' comes from Old English and Germanic words meaning 'ground'."
        "mars" -> "Mars has the largest canyon in the solar system - Valles Marineris is 4,000 km long and 7 km deep! That's 10 times longer than the Grand Canyon."
        "jupiter" -> "Jupiter's Great Red Spot is actually a giant storm that's been raging for over 400 years! It's so big that three Earths could fit inside it."
        "saturn" -> "Saturn's rings are made of billions of pieces of ice and rock, some as small as a grain of sand and others as large as a house!"
        "uranus" -> "Uranus was the first planet discovered with a telescope! It was found by William Herschel in 1781, and he originally wanted to name it 'George's Star'."
        "neptune" -> "Neptune was discovered through math before it was seen! Scientists predicted its existence by studying Uranus's orbit and noticing gravitational disturbances."
        "pluto" -> "Pluto has a heart-shaped glacier called 'Tombaugh Regio'! It's made of nitrogen ice and is about 1,000 km across."
        else -> "Every planet in our solar system is unique and fascinating in its own way!"
    }
}

@Composable
fun PlanetInfoSheetPreview() {
    val samplePlanet = Planet(
        id = "earth",
        name = "Earth",
        distanceFromSun = "1 AU",
        orbitTime = "365.25 Earth days",
        gravity = "9.81 m/s²",
        surface = "Rocky",
        moons = "1",
        description = "Our home planet, Earth, is the only known planet with life. It has liquid water and a protective atmosphere.",
        lottieAnimation = "earth.json",
        color = EarthBlue
    )
    
    AnimatedPlanetBottomSheet(
        planet = samplePlanet,
        onDismiss = {}
    )
} 