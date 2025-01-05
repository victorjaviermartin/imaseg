package com.victormartin.imaseg.ui.screens.permissions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestPermissionsScreen(
    onPermissionsGranted: () -> Unit
) {
    val cameraPermissionState = rememberPermissionState(permission = android.Manifest.permission.CAMERA)
    val readStoragePermissionState = rememberPermissionState(permission = android.Manifest.permission.READ_EXTERNAL_STORAGE)

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        // Solicita los permisos al entrar
        if (!cameraPermissionState.status.isGranted || !readStoragePermissionState.status.isGranted) {
            cameraPermissionState.launchPermissionRequest()
            readStoragePermissionState.launchPermissionRequest()
        } else {
            onPermissionsGranted()
        }
    }

    when {
        cameraPermissionState.status.isGranted && readStoragePermissionState.status.isGranted -> {
            Text("Permisos otorgados. ¡Gracias!")
            onPermissionsGranted()
        }
        else -> {
            Text("Por favor, otorga los permisos necesarios para continuar.")
        }
    }
}
