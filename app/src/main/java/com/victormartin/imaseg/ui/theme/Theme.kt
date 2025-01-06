package com.victormartin.imaseg.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

// Esquema de colores claros
private val DarkColorScheme = darkColorScheme(
    primary = LightGreen, // Color primario en tema claro
    secondary = Yellow, // Color secundario en tema claro
    tertiary = Orange, // Tercero para detalles
    background = LightBackground, // Fondo claro
    surface = LightBackground, // Superficie también clara
    onPrimary = LightGreenContrast, // Contraste oscuro para el texto en primario
    onSecondary = LightGreenContrast, // Contraste oscuro para el texto en secundario
    onTertiary = LightGreenContrast, // Contraste oscuro para el texto en terciario
    onBackground = DarkGreenContrast, // Contraste oscuro para el texto en el fondo claro
    onSurface = DarkGreen, // Contraste oscuro para el texto en la superficie
    primaryContainer = DarkGreen,
    onPrimaryContainer = DarkGreenContrast,
    secondaryContainer = Orange,
    onSecondaryContainer = OrangeContrast
)

// Esquema de colores claros
private val LightColorScheme = lightColorScheme(
    primary = LightGreen, // Color primario en tema claro
    secondary = Yellow, // Color secundario en tema claro
    tertiary = Orange, // Tercero para detalles
    background = LightBackground, // Fondo claro
    surface = LightBackground, // Superficie también clara
    onPrimary = LightGreenContrast, // Contraste oscuro para el texto en primario
    onSecondary = LightGreenContrast, // Contraste oscuro para el texto en secundario
    onTertiary = LightGreenContrast, // Contraste oscuro para el texto en terciario
    onBackground = DarkGreen, // Contraste oscuro para el texto en el fondo claro
    onSurface = DarkGreen, // Contraste oscuro para el texto en la superficie
    primaryContainer = LightGreen,
    onPrimaryContainer = DarkGreenContrast,
    secondaryContainer = Orange,
    onSecondaryContainer = OrangeContrast
)

@Composable
fun ImasegTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
