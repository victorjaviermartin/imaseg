package com.victormartin.imaseg.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.victormartin.imaseg.ui.components.core.InteractiveSegmentationHelper
import com.victormartin.imaseg.ui.components.FabGroup
import com.victormartin.imaseg.ui.components.SegmentationContainer
import com.victormartin.imaseg.ui.components.core.rememberImageHandlerState

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    padding: PaddingValues,
    onError: (String) -> Unit
) {
    var showFabs by remember { mutableStateOf(false) }
    var resultBundle by remember { mutableStateOf<InteractiveSegmentationHelper.ResultBundle?>(null) }
    val context = LocalContext.current

    val helper = remember {
        InteractiveSegmentationHelper(
            context = context,
            onError = onError,
            onResults = { result -> resultBundle = result }
        )
    }

    val imageHandlerState = rememberImageHandlerState(
        onImageCaptured = { bitmap ->
            helper.inputImage = bitmap
            showFabs = false
        }
    )

    Scaffold(
        floatingActionButton = {
            FabGroup(
                showFabs = showFabs,
                onMainFabClick = { showFabs = !showFabs },
                onTakePictureClick = { imageHandlerState.takePicture() },
                onPickImageClick = { imageHandlerState.pickImage() }
            )
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            SegmentationContainer(
                helper = helper,
                resultBundle = resultBundle,
                onTouch = { x, y -> helper.segment(x, y) }
            )
        }
    }
}