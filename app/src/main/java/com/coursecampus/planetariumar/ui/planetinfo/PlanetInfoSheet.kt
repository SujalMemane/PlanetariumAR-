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
            PlanetInfoBottomDrawer(
                planet = planet,
                onDismiss = onDismiss
            )
        }
    }
}

@Composable
fun PlanetInfoBottomDrawer(
    planet: Planet,
    onDismiss: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = tween(100),
        label = "content scale"
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
    
    // Full-screen meteors background - covering entire screen
    val skyMeteorsComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sky_meteors)
    )
    val skyMeteorsProgress by animateLottieCompositionAsState(
        composition = skyMeteorsComposition,
        isPlaying = true,
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
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
        // Full-screen meteors background - covering entire screen
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize()
        )
        
        // Planet floating at the TOP of the screen
        Box(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 60.dp)
        ) {
            LottieAnimation(
                composition = planetComposition,
                progress = { planetProgress },
                modifier = Modifier.size(350.dp) // Large floating planet at top
            )
        }
        
        // Beautiful Bottom Drawer with planet info
        Card(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .heightIn(max = 600.dp) // Limit height for drawer feel
                .padding(16.dp)
                .scale(scale)
                .clickable { isPressed = true },
            shape = RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp), // Rounded top corners for drawer
            colors = CardDefaults.cardColors(
                containerColor = DeepSpace.copy(alpha = 0.95f) // More opaque
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 32.dp // High elevation for floating drawer effect
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                // Drawer handle
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(width = 60.dp, height = 6.dp)
                            .background(
                                color = MeteorGray.copy(alpha = 0.6f),
                                shape = RoundedCornerShape(3.dp)
                            )
                    )
                }
                
                // Header with close button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = planet.name,
                        fontSize = 42.sp, // Larger text
                        fontWeight = FontWeight.Bold,
                        color = NeonBlue
                    )
                    
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.size(64.dp) // Larger button
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close",
                            tint = StarWhite,
                            modifier = Modifier.size(36.dp) // Larger icon
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(24.dp))
                
                // Enhanced description card with better design
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = StellarPurple.copy(alpha = 0.5f) // More visible
                    ),
                    shape = RoundedCornerShape(24.dp), // More rounded
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 12.dp
                    )
                ) {
                    Text(
                        text = planet.description,
                        fontSize = 20.sp, // Larger text
                        color = StarWhite,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(28.dp)
                    )
                }
                
                Spacer(modifier = Modifier.height(28.dp))
                
                // Enhanced planet facts with better design
                PlanetFactsSection(planet)
                
                Spacer(modifier = Modifier.height(20.dp))
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
            text = "Amazing Facts About ${planet.name}",
            fontSize = 28.sp, // Larger text
            fontWeight = FontWeight.Bold,
            color = NeonGreen,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 24.dp)
        )
        
        FunFactRow("Distance from Sun", planet.distanceFromSun)
        FunFactRow("Orbit Time", planet.orbitTime)
        FunFactRow("Gravity", planet.gravity)
        FunFactRow("Surface", planet.surface)
        FunFactRow("Moons", planet.moons)
    }
}

@Composable
fun FunFactRow(
    label: String,
    value: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // More spacing
        colors = CardDefaults.cardColors(
            containerColor = MeteorGray.copy(alpha = 0.4f) // More visible
        ),
        shape = RoundedCornerShape(20.dp), // More rounded
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp), // More padding
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = label,
                fontSize = 18.sp, // Larger text
                color = MeteorGray,
                fontWeight = FontWeight.Medium
            )
            
            Text(
                text = value,
                fontSize = 18.sp, // Larger text
                color = StarWhite,
                fontWeight = FontWeight.Bold
            )
        }
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
    
    PlanetInfoBottomDrawer(
        planet = samplePlanet,
        onDismiss = {}
    )
} 