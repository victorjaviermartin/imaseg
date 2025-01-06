package com.victormartin.imaseg.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.victormartin.imaseg.R
import com.victormartin.imaseg.ui.theme.ImasegTheme

@Composable
fun FabGroup(
    showFabs: Boolean,
    onMainFabClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onPickImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Usar contentColorFor para obtener automáticamente el color de contraste
    val primaryContrastColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.primaryContainer)
    val secondaryContrastColor = MaterialTheme.colorScheme.contentColorFor(MaterialTheme.colorScheme.secondaryContainer)

    // Altura común para todos los botones
    val fabHeight = 72.dp
    val iconSize = 32.dp // Tamaño del icono
    val horizontalPadding = 16.dp // Padding horizontal

    Column(
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = showFabs,
            enter = fadeIn() + expandVertically(),
            exit = fadeOut() + shrinkVertically()
        ) {
            Column(
                horizontalAlignment = Alignment.End,  // Centrar los botones horizontalmente
            ) {
                ExtendedFloatingActionButton(
                    onClick = onTakePictureClick,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(fabHeight), // Establecer altura fija
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.outline_photo_camera_24),
                            contentDescription = "Take Picture",
                            modifier = Modifier
                                .padding(start = horizontalPadding) // Agregar padding horizontal
                                .size(iconSize), // Ajustar el tamaño del icono
                            tint = primaryContrastColor // Usar el color de texto contrastante
                        )
                    },
                    text = {
                        Text(
                            "Take Picture",
                            modifier = Modifier.padding(end = horizontalPadding), // Agregar padding horizontal
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = primaryContrastColor // Usar el color de texto contrastante
                            )
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = CircleShape // Forma rectangular con esquinas lo más redondeadas posible
                )
                ExtendedFloatingActionButton(
                    onClick = onPickImageClick,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .height(fabHeight), // Establecer altura fija
                    icon = {
                        Icon(
                            painterResource(id = R.drawable.outline_photo_library_24),
                            contentDescription = "Pick Image",
                            modifier = Modifier
                                .padding(start = horizontalPadding) // Agregar padding horizontal
                                .size(iconSize), // Ajustar el tamaño del icono
                            tint = primaryContrastColor // Usar el color de texto contrastante
                        )
                    },
                    text = {
                        Text(
                            "Pick Image",
                            modifier = Modifier.padding(end = horizontalPadding), // Agregar padding horizontal
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = primaryContrastColor // Usar el color de texto contrastante
                            )
                        )
                    },
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    shape = CircleShape // Forma rectangular con esquinas lo más redondeadas posible
                )
            }
        }

        // El FloatingActionButton principal siempre estará visible
        FloatingActionButton(
            onClick = onMainFabClick,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            shape = CircleShape, // Forma rectangular con esquinas lo más redondeadas posible
            modifier = Modifier
                .align(Alignment.End)
                .size(fabHeight) // Usar el mismo tamaño para el FAB principal
        ) {
            AnimatedContent(
                targetState = showFabs, // Dependiendo de showFabs, cambia el ícono
                transitionSpec = {
                    fadeIn(tween(durationMillis = 300)) togetherWith scaleOut(tween(durationMillis = 300)) // Cambiar with por togetherWith
                }, label = "FABAnim"
            ) { targetState ->
                if (targetState) {
                    // Si showFabs es true, se muestra el ícono de Close
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Hide options",
                        modifier = Modifier.size(iconSize), // Ajustar el tamaño del icono
                        tint = primaryContrastColor // Usar el color de contraste para el icono
                    )
                } else {
                    // Si showFabs es false, se muestra el icono de Photo Library (de tipo Drawable)
                    Icon(
                        painter = painterResource(id = R.drawable.outline_menu_24),
                        contentDescription = "Show options",
                        modifier = Modifier.size(iconSize), // Ajustar el tamaño del icono
                        tint = primaryContrastColor // Usar el color de contraste para el icono
                    )
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FabGroupPreview() {
    // Definir funciones para los clics en los botones
    val onMainFabClick: () -> Unit = {}
    val onTakePictureClick: () -> Unit = {}
    val onPickImageClick: () -> Unit = {}

    // Mostrar el componente FabGroup con un valor predeterminado de showFabs
    ImasegTheme {
        FabGroup(
            showFabs = true,
            onMainFabClick = onMainFabClick,
            onTakePictureClick = onTakePictureClick,
            onPickImageClick = onPickImageClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FabGroupHidePreview() {
    // Definir funciones para los clics en los botones
    val onMainFabClick: () -> Unit = {}
    val onTakePictureClick: () -> Unit = {}
    val onPickImageClick: () -> Unit = {}

    // Mostrar el componente FabGroup con un valor predeterminado de showFabs
    ImasegTheme {
        FabGroup(
            showFabs = false,
            onMainFabClick = onMainFabClick,
            onTakePictureClick = onTakePictureClick,
            onPickImageClick = onPickImageClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme", uiMode = 32)
@Composable
fun FabGroupPreviewDark() {
    // Definir funciones para los clics en los botones
    val onMainFabClick: () -> Unit = {}
    val onTakePictureClick: () -> Unit = {}
    val onPickImageClick: () -> Unit = {}

    // Mostrar el componente FabGroup en tema oscuro
    ImasegTheme(
        darkTheme = true
    ) {
        FabGroup(
            showFabs = true,
            onMainFabClick = onMainFabClick,
            onTakePictureClick = onTakePictureClick,
            onPickImageClick = onPickImageClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Preview(showBackground = true, name = "Dark Theme", uiMode = 32)
@Composable
fun FabGroupHidePreviewDark() {
    // Definir funciones para los clics en los botones
    val onMainFabClick: () -> Unit = {}
    val onTakePictureClick: () -> Unit = {}
    val onPickImageClick: () -> Unit = {}

    // Mostrar el componente FabGroup en tema oscuro
    ImasegTheme(
        darkTheme = true
    ) {
        FabGroup(
            showFabs = false,
            onMainFabClick = onMainFabClick,
            onTakePictureClick = onTakePictureClick,
            onPickImageClick = onPickImageClick,
            modifier = Modifier.fillMaxSize()
        )
    }
}
