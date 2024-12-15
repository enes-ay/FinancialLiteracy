package com.enesay.financialliteracy.ui.theme

import android.annotation.SuppressLint
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = primary_color,
    onPrimary = WhiteColor,
    secondary = secondary_color,
    tertiary = Pink80,
    onSecondaryContainer = GrayColor,
    background = BlackColor
)

private val LightColorScheme = lightColorScheme(
    primary = primary_color,
    secondary = PurpleGrey40,
    onPrimary = BlackColor,
    onSecondary = GrayColor,
    onSecondaryContainer = WhiteColor,
    tertiary = Pink40,
    background = WhiteColor
)

@Composable
fun FinancialLiteracyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}