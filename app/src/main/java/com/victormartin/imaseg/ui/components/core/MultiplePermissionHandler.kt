package com.victormartin.imaseg.ui.components.core

import android.content.Intent
import android.net.Uri
import android.os.Build
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
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.isGranted

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MultiplePermissionsHandler(content: @Composable () -> Unit) {
    val permissions = remember {
        mutableListOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.INTERNET,
            android.Manifest.permission.ACCESS_NETWORK_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ).apply {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> {
                    add(android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED)
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                    add(android.Manifest.permission.READ_MEDIA_IMAGES)
                }
                Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2 -> {
                    add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    val permissionsState = rememberMultiplePermissionsState(permissions)

    // Solicitar permisos automáticamente cuando el componente se monta
    LaunchedEffect(permissionsState) {
        // Lanzar la solicitud de permisos automáticamente
        permissionsState.launchMultiplePermissionRequest()
    }

    if (permissionsState.allPermissionsGranted) {
        content()
    } else {
        PermissionsRationale(
            permissionsState = permissionsState
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionsRationale(
    permissionsState: com.google.accompanist.permissions.MultiplePermissionsState
) {
    val context = LocalContext.current
    val pendingPermissions = permissionsState.permissions.filterNot { it.status.isGranted }

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
                text = buildString {
                    append("La aplicación necesita los siguientes permisos:\n\n")
                    pendingPermissions.forEach { permission ->
                        append("• ${getPermissionText(permission)}\n")
                    }
                },
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )

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

@OptIn(ExperimentalPermissionsApi::class)
private fun getPermissionText(permission: PermissionState): String {
    return when (permission.permission) {
        android.Manifest.permission.CAMERA -> "Cámara"
        android.Manifest.permission.READ_MEDIA_IMAGES -> "Acceso a imágenes"
        android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED -> "Acceso a imágenes seleccionadas"
        android.Manifest.permission.READ_EXTERNAL_STORAGE -> "Acceso al almacenamiento"
        else -> permission.permission
    }
}
