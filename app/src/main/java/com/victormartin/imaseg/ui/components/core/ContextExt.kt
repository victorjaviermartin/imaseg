package com.victormartin.imaseg.ui.components.core

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File


// Extension function for Context to create the image URI
fun Context.createImageUri(): Uri {
    val uniqueIdentifier = FileUtils.generateUniqueIdentifier()
    val fileName = FileUtils.generateFileName(uniqueIdentifier)
    val file = File(cacheDir, fileName)
    return FileProvider.getUriForFile(
        this,
        "$packageName.fileprovider",
        file
    )
}