package com.victormartin.imaseg.ui.components.core

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun SinglePermissionHandler(content: @Composable () -> Unit) {
    // Recordamos el estado del permiso de cámara
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    // Si el permiso es concedido, mostramos el contenido
    if (cameraPermissionState.status.isGranted) {
        content()
    } else {
        // Si el permiso no es concedido, mostramos la justificación y un botón para solicitarlo
        CameraPermissionRationale(
            permissionState = cameraPermissionState
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermissionRationale(
    permissionState: PermissionState
) {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .padding(WindowInsets.statusBars.asPaddingValues())
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "La aplicación necesita acceso a la cámara para funcionar.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

            // Botón para solicitar el permiso
            Button(onClick = { permissionState.launchPermissionRequest() }) {
                Text("Solicitar permiso")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                // Abrir la configuración de la app para conceder el permiso manualmente
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            }) {
                Text("Abrir configuración")
            }
        }
    }
}
