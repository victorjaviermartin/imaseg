package com.victormartin.imaseg.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.slideOutVertically as slideOutVertically1

@Composable
fun FabGroup(
    showFabs: Boolean,
    onMainFabClick: () -> Unit,
    onTakePictureClick: () -> Unit,
    onPickImageClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End
    ) {
        AnimatedVisibility(
            visible = showFabs,
            enter = fadeIn() + slideInVertically(),
            exit = fadeOut() + slideOutVertically1()
        ) {
            Column {
                ExtendedFloatingActionButton(
                    onClick = onTakePictureClick,
                    modifier = Modifier.padding(bottom = 8.dp),
                    icon = { Icon(Icons.Default.AddCircle, "Take Picture") },
                    text = { Text("Take Picture") }
                )
                ExtendedFloatingActionButton(
                    onClick = onPickImageClick,
                    modifier = Modifier.padding(bottom = 16.dp),
                    icon = { Icon(Icons.Filled.Search, "Pick Image") },
                    text = { Text("Pick Image") }
                )
            }
        }
        FloatingActionButton(onClick = onMainFabClick) {
            Icon(
                imageVector = if (showFabs) Icons.Default.Close else Icons.Default.Add,
                contentDescription = if (showFabs) "Hide options" else "Show options"
            )
        }
    }
}