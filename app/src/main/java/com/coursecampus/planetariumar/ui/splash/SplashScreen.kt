package com.coursecampus.planetariumar.ui.splash

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
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
import com.coursecampus.planetariumar.ui.theme.DeepSpace
import com.coursecampus.planetariumar.ui.theme.NeonBlue
import com.coursecampus.planetariumar.ui.theme.NeonGreen
import com.coursecampus.planetariumar.ui.theme.StarWhite
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onNavigateToHome: () -> Unit
) {
    var rocketFlying by remember { mutableStateOf(false) }
    var showText by remember { mutableStateOf(false) }
    
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
    
    // Rocket animation states
    val rocketScale by animateFloatAsState(
        targetValue = if (rocketFlying) 0.2f else 1f,
        animationSpec = tween(1500),
        label = "rocket scale"
    )
    
    val rocketOffset by animateFloatAsState(
        targetValue = if (rocketFlying) -800f else 0f,
        animationSpec = tween(1500),
        label = "rocket offset"
    )
    
    val rocketAlpha by animateFloatAsState(
        targetValue = if (rocketFlying) 0f else 1f,
        animationSpec = tween(1500),
        label = "rocket alpha"
    )
    
    val textAlpha by animateFloatAsState(
        targetValue = if (rocketFlying) 0f else 1f,
        animationSpec = tween(500),
        label = "text alpha"
    )
    
    // Lottie composition for rocket launch
    val rocketLaunchComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.rocket_launch)
    )
    val rocketLaunchProgress by animateLottieCompositionAsState(
        composition = rocketLaunchComposition,
        isPlaying = true,
        speed = 0.9f, // Slower speed for more elegant animation
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    // Lottie composition for sky meteors
    val skyMeteorsComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.sky_meteors)
    )
    val skyMeteorsProgress by animateLottieCompositionAsState(
        composition = skyMeteorsComposition,
        isPlaying = true,
        speed = 0.9f, // Slower speed for more elegant animation
        iterations = com.airbnb.lottie.compose.LottieConstants.IterateForever
    )
    
    LaunchedEffect(Unit) {
        delay(800)
        showText = true
    }
    
    // Handle navigation after rocket animation
    LaunchedEffect(rocketFlying) {
        if (rocketFlying) {
            delay(1000)
            onNavigateToHome()
        }
    }
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black) // Pure black background first
            .clickable(
                enabled = showText && !rocketFlying
            ) {
                rocketFlying = true
            }
    ) {
        // Full-screen sky meteors background animation - NO PADDING
        LottieAnimation(
            composition = skyMeteorsComposition,
            progress = { skyMeteorsProgress },
            modifier = Modifier.fillMaxSize() // Fill entire screen
        )
        
        // Animated stars background overlay
        repeat(30) { index ->
            val x = (index * 47) % 400
            val y = (index * 83) % 800
            val size = (index % 3 + 1) * 2
            
            Box(
                modifier = Modifier
                    .offset(x.dp, y.dp)
                    .size(size.dp)
                    .alpha(starAlpha * 0.8f)
                    .background(
                        color = StarWhite,
                        shape = CircleShape
                    )
            )
        }
        
        // Rocket in center - flies UP and shrinks when tapped
        Box(
            modifier = Modifier
                .align(Alignment.Center)
                .offset(y = rocketOffset.dp) // Positive offset makes it go UP to top
                .scale(rocketScale)
                .alpha(rocketAlpha)
        ) {
            LottieAnimation(
                composition = rocketLaunchComposition,
                progress = { rocketLaunchProgress },
                modifier = Modifier.size(280.dp) // Large rocket
            )
        }
        
        // Text below rocket - "Tap to enter solar system" - ENHANCED
        if (showText) {
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 140.dp)
                    .alpha(textAlpha),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tap to enter solar system",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = NeonBlue, // Blue color
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(16.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Enhanced instruction text
                Text(
                    text = "Ready for space adventure?",
                    style = androidx.compose.material3.MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = StarWhite.copy(alpha = 0.8f),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(8.dp)
                )
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