package com.coursecampus.planetariumar.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = NeonBlue,
    secondary = NeonGreen,
    tertiary = StellarPurple,
    background = SpaceBlack,
    surface = DeepSpace,
    onPrimary = StarWhite,
    onSecondary = SpaceBlack,
    onTertiary = StarWhite,
    onBackground = StarWhite,
    onSurface = StarWhite,
    primaryContainer = CosmicBlue,
    secondaryContainer = MeteorGray,
    tertiaryContainer = StellarPurple,
    onPrimaryContainer = StarWhite,
    onSecondaryContainer = SpaceBlack,
    onTertiaryContainer = StarWhite
)

private val LightColorScheme = lightColorScheme(
    primary = NeonBlue,
    secondary = NeonGreen,
    tertiary = StellarPurple,
    background = SpaceBlack,
    surface = DeepSpace,
    onPrimary = StarWhite,
    onSecondary = SpaceBlack,
    onTertiary = StarWhite,
    onBackground = StarWhite,
    onSurface = StarWhite,
    primaryContainer = CosmicBlue,
    secondaryContainer = MeteorGray,
    tertiaryContainer = StellarPurple,
    onPrimaryContainer = StarWhite,
    onSecondaryContainer = SpaceBlack,
    onTertiaryContainer = StarWhite
)

@Composable
fun PlanetariumARTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            // Make the system bars transparent and extend the app behind them
            WindowCompat.setDecorFitsSystemWindows(window, false)
            
            // Set status bar and navigation bar colors to black for space theme
            window.statusBarColor = Color.Black.toArgb()
            window.navigationBarColor = Color.Black.toArgb()
            
            // Make status bar icons light (white) for dark background
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}