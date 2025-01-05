package com.victormartin.imaseg.ui.components.core

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun rememberImageHandlerState(
    onImageCaptured: (Bitmap) -> Unit
): ImageHandlerState {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Launcher for taking a picture (full resolution)
    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success ->
        if (success) {
            // Si la foto se toma correctamente, carga la imagen
            val uri = context.createImageUri()
            scope.launch {
                val bitmap = loadBitmapFromUri(context, uri)
                bitmap?.let {
                    // Guardar la imagen como PNG
                    val savedFile = FileUtils.saveBitmapAsPng(context, FileUtils.generateUniqueIdentifier(), it)
                    onImageCaptured(it) // Puedes hacer algo más con el archivo guardado si es necesario
                }
            }
        }
    }

    // Launcher for picking an image
    // Registers a photo picker activity launcher in single-select mode.
    val pickImageLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri: Uri? ->
            uri?.let {
                scope.launch {
                    val bitmap = loadBitmapFromUri(context, uri)
                    bitmap?.let { onImageCaptured(it) }
                }
            }
        }

    return remember(context, takePictureLauncher, pickImageLauncher) {
        ImageHandlerState(
            context = context,
            takePictureLauncher = takePictureLauncher,
            pickImageLauncher = pickImageLauncher,
            onImageCaptured = onImageCaptured
        )
    }
}

// Helper function to load a bitmap from a URI
private suspend fun loadBitmapFromUri(context: android.content.Context, uri: Uri): Bitmap? {
    return withContext(Dispatchers.IO) {
        try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}