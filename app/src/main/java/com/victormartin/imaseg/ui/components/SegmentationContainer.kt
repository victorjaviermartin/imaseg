package com.victormartin.imaseg.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.victormartin.imaseg.ui.components.core.InteractiveSegmentationHelper

@Composable
fun SegmentationContainer(
    helper: InteractiveSegmentationHelper,
    resultBundle: InteractiveSegmentationHelper.ResultBundle?,
    onTouch: (Float, Float) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedPoint by remember { mutableStateOf<Pair<Float, Float>?>(null) }

    Box(modifier = modifier.fillMaxSize()) {
        ImageContent(
            bitmap = helper.inputImage,
            onTouch = { x, y ->
                selectedPoint = x to y
                onTouch(x, y)
            }
        )

        SegmentationOverlay(
            resultBundle = resultBundle,
            selectedPoint = selectedPoint
        )

        if (helper.inputImage == null) {
            EmptyStateMessage()
        }
    }
}