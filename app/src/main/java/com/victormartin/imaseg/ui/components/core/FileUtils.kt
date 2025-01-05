package com.victormartin.imaseg.ui.components.core

import android.graphics.Bitmap
import java.io.File
import java.io.FileOutputStream
import java.util.UUID

object FileUtils {
    private const val FILE_NAMING_SUFFIX = "IMASEG"
    private const val FILE_FORMAT_JPG = "jpg"
    private const val FILE_FORMAT_PNG = "png"

    private val photoTakenCounter = mutableMapOf<String, Int>()

    /**
     * Generates a file name using a unique identifier, suffix, photo number, and format.
     * @param identifierFile Unique identifier for the file, e.g., user ID or session ID.
     * @return Generated file name in the format: {identifierFile}_{FILE_NAMING_SUFFIX}_{photoTakenNumber}.jpg
     */
    fun generateFileName(identifierFile: String, fileFormat: String = FILE_FORMAT_JPG): String {
        val currentPhotoNumber = (photoTakenCounter[identifierFile] ?: 0) + 1
        photoTakenCounter[identifierFile] = currentPhotoNumber

        return "${identifierFile}_${FILE_NAMING_SUFFIX}_${System.currentTimeMillis()}.$fileFormat"
    }


    /**
     * Generates a unique identifier of exactly 16 characters.
     * @return A unique 16-character identifier string.
     */
    fun generateUniqueIdentifier(): String {
        return UUID.randomUUID().toString().replace("-", "")
    }

    fun saveBitmapAsPng(context: android.content.Context, identifierFile: String, bitmap: Bitmap): File {
        val fileName = generateFileName(identifierFile, FILE_FORMAT_PNG) // Usar el identificador proporcionado
        val file = File(context.cacheDir, fileName)

        FileOutputStream(file).use { out ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out) // Guardamos la imagen como PNG
        }

        return file
    }
}
