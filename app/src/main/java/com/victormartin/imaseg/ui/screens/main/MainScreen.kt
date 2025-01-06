package com.victormartin.imaseg.ui.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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

    Scaffold(modifier = Modifier
        .captionBarPadding()
        .imePadding(),
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    var showFabs by remember { mutableStateOf(true) } // Empieza con los botones desplegados
    var resultBundle by remember { mutableStateOf<InteractiveSegmentationHelper.ResultBundle?>(null) }
    val context = LocalContext.current

    val helper = remember {
        InteractiveSegmentationHelper(
            context = context,
            onError = {},
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
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)  // Añadir margen a la derecha
                    .fillMaxWidth()  // Ocupa todo el ancho disponible
            ) {
                FabGroup(
                    showFabs = showFabs,
                    onMainFabClick = { showFabs = !showFabs },
                    onTakePictureClick = { imageHandlerState.takePicture() },
                    onPickImageClick = { imageHandlerState.pickImage() }
                )
            }
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreviewCollapsed() {
    var showFabs by remember { mutableStateOf(false) } // Empieza con los botones colapsados
    var resultBundle by remember { mutableStateOf<InteractiveSegmentationHelper.ResultBundle?>(null) }
    val context = LocalContext.current

    val helper = remember {
        InteractiveSegmentationHelper(
            context = context,
            onError = {},
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
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)  // Añadir margen a la derecha
                    .fillMaxWidth()  // Ocupa todo el ancho disponible
            ) {
                FabGroup(
                    showFabs = showFabs,
                    onMainFabClick = { showFabs = !showFabs },
                    onTakePictureClick = { imageHandlerState.takePicture() },
                    onPickImageClick = { imageHandlerState.pickImage() }
                )
            }
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
