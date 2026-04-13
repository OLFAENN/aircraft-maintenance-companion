package com.olfa.aircraftmaintenance.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val AppLightColors = lightColorScheme(
    primary = OceanBlue,
    onPrimary = Color.White,

    secondary = NavyDark,
    onSecondary = Color.White,

    tertiary = SkyBlue,
    onTertiary = Color.White,

    background = SurfaceSoft,
    onBackground = TextDark,

    surface = Color.White,
    onSurface = TextDark,

    surfaceVariant = BlueSoft,
    onSurfaceVariant = TextSoft,

    outline = BorderSoft,

    primaryContainer = LavenderSoft,
    onPrimaryContainer = TextDark,

    secondaryContainer = BlueSoft,
    onSecondaryContainer = TextDark,

    tertiaryContainer = PeachSoft,
    onTertiaryContainer = TextDark,

    error = DangerRed,
    onError = Color.White
)

@Composable
fun AircraftMaintenanceCompanionTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = AppLightColors,
        typography = Typography,
        content = content
    )
}