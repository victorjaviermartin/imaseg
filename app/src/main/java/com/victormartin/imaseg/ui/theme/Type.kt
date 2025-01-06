package com.victormartin.imaseg.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.victormartin.imaseg.R

// Declarar las familias tipográficas
object AppFont {
    val Lato = FontFamily(
        Font(R.font.lato_thin, FontWeight.Thin),
        Font(R.font.lato_thinitalic, FontWeight.Thin, style = FontStyle.Italic),
        Font(R.font.lato_light, FontWeight.Light),
        Font(R.font.lato_lightitalic, FontWeight.Light, style = FontStyle.Italic),
        Font(R.font.lato_regular, FontWeight.Normal),
        Font(R.font.lato_italic, FontWeight.Normal, style = FontStyle.Italic),
        Font(R.font.lato_bold, FontWeight.Bold),
        Font(R.font.lato_bolditalic, FontWeight.Bold, style = FontStyle.Italic),
        Font(R.font.lato_black, FontWeight.Black),
        Font(R.font.lato_blackitalic, FontWeight.Black, style = FontStyle.Italic)
    )
}

// Crear la tipografía basada en Lato
val Typography = Typography(
    displayLarge = Typography().displayLarge.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Black,
        fontSize = 57.sp // Tamaño común para títulos grandes
    ),
    displayMedium = Typography().displayMedium.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Black,
        fontSize = 45.sp // Tamaño para títulos medianos
    ),
    displaySmall = Typography().displaySmall.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 35.sp // Tamaño para títulos pequeños
    ),

    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp // Tamaño para encabezados grandes
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp // Tamaño para encabezados medianos
    ),
    headlineSmall = Typography().headlineSmall.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp // Tamaño para encabezados pequeños
    ),

    titleLarge = Typography().titleLarge.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp // Tamaño para títulos grandes
    ),
    titleMedium = Typography().titleMedium.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp // Tamaño para títulos medianos
    ),
    titleSmall = Typography().titleSmall.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp // Tamaño para títulos pequeños
    ),

    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp // Tamaño para texto de cuerpo grande
    ),
    bodyMedium = Typography().bodyMedium.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp // Tamaño para texto de cuerpo medio
    ),
    bodySmall = Typography().bodySmall.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp // Tamaño para texto de cuerpo pequeño
    ),

    labelLarge = Typography().labelLarge.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp // Tamaño para etiquetas grandes
    ),
    labelMedium = Typography().labelMedium.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp // Tamaño para etiquetas medianas
    ),
    labelSmall = Typography().labelSmall.copy(
        fontFamily = AppFont.Lato,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp // Tamaño para etiquetas pequeñas
    )
)