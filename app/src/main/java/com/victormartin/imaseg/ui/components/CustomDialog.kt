package com.victormartin.imaseg.ui.components

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CustomDialog(showDialog:Boolean, onChanged:(isShown:Boolean) -> Unit, properties: DialogProperties = DialogProperties(),
                 content: @Composable () -> Unit){

    val animation by remember {
        mutableStateOf(androidx.compose.animation.core.Animatable(initialValue = 1f))
    }

    var show by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = showDialog){

        if (showDialog) show = true
        launch {
            if (showDialog.not() and show) {
                delay(350)
                show = false
            }
        }
        launch {
            delay(75)
            animation.animateTo(targetValue = 1.05f,
                animationSpec = spring(Spring.DampingRatioMediumBouncy,Spring.StiffnessMediumLow))
        }

        launch {
            delay(175)
            animation.animateTo(targetValue = 1f,
                animationSpec = spring(Spring.DampingRatioMediumBouncy,Spring.StiffnessMediumLow))
        }

    }

    if (show){
        Dialog(onDismissRequest = {
            onChanged(false)
        }, properties = properties) {
            Box(modifier = Modifier.scale(animation.value)){
                content()
            }
        }
    }

}