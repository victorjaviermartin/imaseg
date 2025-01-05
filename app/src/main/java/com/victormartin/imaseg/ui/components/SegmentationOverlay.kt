package com.victormartin.imaseg.ui.components

// File: ui/components/SegmentationOverlay.kt
import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.victormartin.imaseg.ui.components.core.InteractiveSegmentationHelper
import java.nio.ByteBuffer
import android.graphics.Color as AndroidColor

@Composable
fun SegmentationOverlay(
    resultBundle: InteractiveSegmentationHelper.ResultBundle?,
    selectedPoint: Pair<Float, Float>?,
    modifier: Modifier = Modifier
) {
    // Colors for the overlay
    val overlayColor = remember { Color(AndroidColor.parseColor("#8012B5CB")) }
    val markerColor = remember { Color(AndroidColor.parseColor("#FBBC04")) }
    val markerBorderColor = remember { Color(AndroidColor.parseColor("#000000")) }

    Canvas(modifier = modifier) {
        // Draw the segmentation mask if available
        resultBundle?.let { bundle ->
            drawSegmentationMask(
                byteBuffer = bundle.byteBuffer,
                maskWidth = bundle.maskWidth,
                maskHeight = bundle.maskHeight,
                overlayColor = overlayColor
            )
        }

        // Draw the selection marker if a point is selected
        selectedPoint?.let { (x, y) ->
            drawSelectionMarker(
                x = x,
                y = y,
                markerColor = markerColor,
                borderColor = markerBorderColor
            )
        }
    }
}

private fun DrawScope.drawSegmentationMask(
    byteBuffer: ByteBuffer,
    maskWidth: Int,
    maskHeight: Int,
    overlayColor: Color
) {
    // Create a bitmap from the byte buffer
    val pixels = IntArray(byteBuffer.capacity())
    for (i in pixels.indices) {
        val index = byteBuffer.get(i).toInt()
        pixels[i] = if (index == 0) {
            AndroidColor.TRANSPARENT
        } else {
            overlayColor.toArgb()
        }
    }

    // Create and scale the bitmap to match the canvas size
    val originalBitmap = Bitmap.createBitmap(
        pixels,
        maskWidth,
        maskHeight,
        Bitmap.Config.ARGB_8888
    )

    // Calculate scaling factors to fit the canvas
    val scaleX = size.width / maskWidth
    val scaleY = size.height / maskHeight
    val scale = minOf(scaleX, scaleY)

    val scaledWidth = (maskWidth * scale).toInt()
    val scaledHeight = (maskHeight * scale).toInt()

    // Scale the bitmap
    val scaledBitmap = Bitmap.createScaledBitmap(
        originalBitmap,
        scaledWidth,
        scaledHeight,
        false
    )

    // Calculate position to center the bitmap
    val left = (size.width - scaledWidth) / 2
    val top = (size.height - scaledHeight) / 2

    // Draw the scaled bitmap
    drawImage(
        image = scaledBitmap.asImageBitmap(),
        topLeft = Offset(left, top)
    )

    // Clean up bitmaps
    originalBitmap.recycle()
    scaledBitmap.recycle()
}

private fun DrawScope.drawSelectionMarker(
    x: Float,
    y: Float,
    markerColor: Color,
    borderColor: Color
) {
    // Draw outer circle (border)
    drawCircle(
        color = borderColor,
        radius = 20f,
        center = Offset(x, y)
    )

    // Draw inner circle (marker)
    drawCircle(
        color = markerColor,
        radius = 15f,
        center = Offset(x, y)
    )
}

// Extension function to convert Color to Android Color int
private fun Color.toArgb(): Int {
    return AndroidColor.argb(
        (alpha * 255).toInt(),
        (red * 255).toInt(),
        (green * 255).toInt(),
        (blue * 255).toInt()
    )
}