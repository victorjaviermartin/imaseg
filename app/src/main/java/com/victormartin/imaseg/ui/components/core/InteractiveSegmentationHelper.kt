package com.victormartin.imaseg.ui.components.core

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.mediapipe.framework.image.BitmapImageBuilder
import com.google.mediapipe.framework.image.ByteBufferExtractor
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.components.containers.NormalizedKeypoint
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.imagesegmenter.ImageSegmenterResult
import com.google.mediapipe.tasks.vision.interactivesegmenter.InteractiveSegmenter
import java.nio.ByteBuffer

class InteractiveSegmentationHelper(
    private val context: Context,
    private val onError: (String) -> Unit,
    private val onResults: (ResultBundle?) -> Unit
) {
    private var interactiveSegmenter: InteractiveSegmenter? = null
    var inputImage by mutableStateOf<Bitmap?>(null)             // This replace setInputImage method

    init {
        setupInteractiveSegmenter()
    }

    fun clear() {
        interactiveSegmenter?.close()
        interactiveSegmenter = null
    }

    private fun setupInteractiveSegmenter() {
        val baseOptionBuilder = BaseOptions.builder().setModelAssetPath(MP_MODEL_MAGIC_TOUCH)

        try {
            val baseOptions = baseOptionBuilder.build()
            val optionsBuilder =
                InteractiveSegmenter.InteractiveSegmenterOptions.builder()
                    .setBaseOptions(baseOptions)
                    .setOutputCategoryMask(true)
                    .setOutputConfidenceMasks(false)
                    .setResultListener(this::returnSegmenterResults)
                    .setErrorListener(this::returnSegmenterError)

            val options = optionsBuilder.build()
            interactiveSegmenter = InteractiveSegmenter.createFromOptions(context, options)
        } catch (e: IllegalStateException) {
            onError("Interactive segmentation failed to initialize. See error logs for details")
            Log.e(TAG, "MP Task Vision failed to load the task with error: " + e.message)
        } catch (e: RuntimeException) {
            onError("Interactive segmentation failed to initialize. See error logs for details")
            Log.e(TAG, "MP Task Vision failed to load the task with error: " + e.message)
        }
    }

//    fun setInputImage(bitmap: Bitmap) {
//        inputImage = bitmap
//    }

    fun isInputImageAssigned(): Boolean = inputImage != null

    fun segment(normX: Float, normY: Float) {
        clear()
        setupInteractiveSegmenter()
        inputImage?.let {
            val roi = InteractiveSegmenter.RegionOfInterest.create(
                NormalizedKeypoint.create(
                    normX * it.width,
                    normY * it.height
                )
            )
            val mpImage = BitmapImageBuilder(it).build()
            interactiveSegmenter?.segmentWithResultListener(mpImage, roi)
        }
    }

    private fun returnSegmenterResults(
        result: ImageSegmenterResult,
        mpImage: MPImage
    ) {
        val byteBuffer = ByteBufferExtractor.extract(result.categoryMask().get())
        val resultBundle = ResultBundle(byteBuffer, mpImage.width, mpImage.height)
        onResults(resultBundle)
    }

    private fun returnSegmenterError(error: RuntimeException) {
        onError(error.message.toString())
    }

    companion object {
        private const val TAG = "InteractiveSegmentationHelper"
        private const val MP_MODEL_MAGIC_TOUCH = "magic_touch.tflite"
        private const val MP_MODEL_DEEPLAB_V3 = "deeplab_v3.tflite"
        private const val MP_MODEL_YOLO_V11 = "yolo_v11.tflite"
    }

    data class ResultBundle(
        val byteBuffer: ByteBuffer,
        val maskWidth: Int,
        val maskHeight: Int
    )
}