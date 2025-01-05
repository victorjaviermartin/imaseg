package com.victormartin.imaseg.ui.components

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput

@Composable
fun ImageContent(
    bitmap: Bitmap?,
    onTouch: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    bitmap?.let { image ->
        Image(
            bitmap = image.asImageBitmap(),
            contentDescription = null,
            modifier = modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        onTouch(
                            offset.x / size.width,
                            offset.y / size.height
                        )
                    }
                }
        )
    }
}