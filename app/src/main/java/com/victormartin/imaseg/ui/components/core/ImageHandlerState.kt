package com.victormartin.imaseg.ui.components.core

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts

// ImageHandlerState class with FileUtils usage for file name generation
class ImageHandlerState(
    private val context: Context,
    private val takePictureLauncher: ManagedActivityResultLauncher<Uri, Boolean>,
    private val pickImageLauncher: ManagedActivityResultLauncher<PickVisualMediaRequest, Uri?>,
    private val onImageCaptured: (Bitmap) -> Unit
) {
    fun takePicture() {
        val uri = context.createImageUri()
        takePictureLauncher.launch(uri)
    }

    fun pickImagePng() {
        // Launch the photo picker and let the user choose only images/videos of a
        // specific MIME type, such as GIFs.
        val mimeType = "image/png"
        pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.SingleMimeType(mimeType)))
    }

    fun pickImage() {
        // Launch the photo picker and let the user choose only images
        pickImageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }
}