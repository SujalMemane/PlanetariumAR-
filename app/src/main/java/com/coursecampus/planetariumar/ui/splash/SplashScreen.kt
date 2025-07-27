package com.coursecampus.planetariumar.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.coursecampus.planetariumar.R
import com.coursecampus.planetariumar.ui.theme.CosmicBlue
import com.coursecampus.planetariumar.ui.theme.DeepSpace
import com.coursecampus.planetariumar.ui.theme.NeonBlue
import com.coursecampus.planetariumar.ui.theme.SpaceBlack
import com.coursecampus.planetariumar.ui.theme.StarWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit
) {
    var titleVisible by remember { mutableStateOf(false) }
    
    val infiniteTransition = rememberInfiniteTransition(label = "stars")
    val starAlpha by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "star twinkle"
    )
    
    // Lottie composition for sky meteors
    val skyMeteorsComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sky_meteors)
    )
    val skyMeteorsProgress by animateLottieCompositionAsState(
        composition = skyMeteorsComposition,
        isPlaying = true,
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    // Lottie composition for rocket launch
    val rocketLaunchComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.rocket_launch)
    )
    val rocketLaunchProgress by animateLottieCompositionAsState(
        composition = rocketLaunchComposition,
        isPlaying = true,
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    LaunchedEffect(Unit) {
        delay(1000)
        titleVisible = true
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Solid black background first
            .background(
                Brush.verticalGradient(
                    colors = listOf(SpaceBlack, DeepSpace, CosmicBlue)
                )
            )
            .clickable(
                enabled = titleVisible
            ) {
                onNavigateToHome()
            }
    ) {
        // Full-screen sky meteors background animation
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize()
        )
        
        // Rocket launch animation in center
        LottieAnimation(
            composition = rocketLaunchComposition,
            progress = { rocketLaunchProgress },
            modifier = Modifier
                .size(200.dp)
                .align(Alignment.Center)
        )
        
        // Animated stars background overlay
        repeat(50) { index ->
            val x = (index * 37) % 400
            val y = (index * 73) % 800
            val size = (index % 3 + 1) * 2
            
            Box(
                modifier = Modifier
                    .offset(x.dp, y.dp)
                    .size(size.dp)
                    .alpha(starAlpha * 0.7f)
                    .background(
                        color = StarWhite,
                        shape = CircleShape
                    )
            )
        }
        
        // Enhanced title with better card design - no emojis
        if (titleVisible) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 80.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Card(
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
                            text = "PlanetariumAR",
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Bold,
                            color = NeonBlue
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Tap to explore the solar system",
                            fontSize = 20.sp,
                            color = StarWhite,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SplashScreenPreview() {
    SplashScreen(
        onNavigateToHome = {}
    )
} 