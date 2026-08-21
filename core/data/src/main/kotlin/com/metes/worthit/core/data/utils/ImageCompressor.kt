package com.metes.worthit.core.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.utils.throwIfNull
import dagger.Reusable
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject

@Reusable
internal class ImageCompressor @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    @param:ApplicationContext private val context: Context,
) {
    suspend fun compress(
        imageUri: Uri,
        outputFile: File,
        reqWidth: Int = 512,
        reqHeight: Int = 512,
        quality: Int = DEFAULT_QUALITY,
        compressFormat: Bitmap.CompressFormat = ImageFormatCompat.webpLossy
    ): Result<Unit, Error> = withContext(ioDispatcher) {
        var sampleBitmap: Bitmap? = null
        var finalBitmap: Bitmap? = null

        try {
            val orientation = getOrientationFromUri(imageUri)

            sampleBitmap = decodeSampleBitmap(imageUri, reqWidth, reqHeight)
            finalBitmap = sampleBitmap.rotated(orientation)

            val isSuccess = outputFile.outputStream().use { output ->
                finalBitmap.compress(compressFormat, quality, output)
            }

            if (isSuccess) {
                return@withContext Result.Success(Unit)
            } else {
                return@withContext Result.Error(FileError.CompressionFailed)
            }
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            return@withContext Result.Error(FileError.CompressionFailed)
        } finally {
            finalBitmap?.recycle()
            if (sampleBitmap !== finalBitmap) {
                sampleBitmap?.recycle()
            }
        }
    }

    private fun decodeSampleBitmap(
        uri: Uri,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        with(context) {
            contentResolver.openInputStream(uri)
                .throwIfNull(IllegalStateException("Failed to open input stream"))
                .use { input ->
                    BitmapFactory.decodeStream(input, null, options)
                }

            options.inSampleSize =
                calculateInSampleSize(options = options, reqWidth = reqWidth, reqHeight = reqHeight)
            options.inJustDecodeBounds = false

            return contentResolver.openInputStream(uri)?.use { input ->
                BitmapFactory.decodeStream(input, null, options)
                    .throwIfNull(IllegalStateException("Failed to decode stream"))
            }.throwIfNull(IllegalStateException("Failed to decode image into sampleBitmap"))
        }
    }


    fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

    private fun Bitmap.rotated(
        orientation: Int
    ): Bitmap {
        val matrix = Matrix()
        when (orientation) {
            ExifInterface.ORIENTATION_UNDEFINED, ExifInterface.ORIENTATION_NORMAL -> return this
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> matrix.setScale(-1f, 1f)
            ExifInterface.ORIENTATION_ROTATE_180 -> matrix.setRotate(180f)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> {
                matrix.setRotate(180f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_TRANSPOSE -> {
                matrix.setRotate(90f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_ROTATE_90 -> matrix.setRotate(90f)
            ExifInterface.ORIENTATION_TRANSVERSE -> {
                matrix.setRotate(-90f)
                matrix.postScale(-1f, 1f)
            }

            ExifInterface.ORIENTATION_ROTATE_270 -> matrix.setRotate(270f)
        }

        val rotatedBitmap = Bitmap.createBitmap(
            this,
            0,
            0,
            width,
            height,
            matrix,
            true
        )

        return rotatedBitmap
    }

    private fun getOrientationFromUri(uri: Uri): Int {
        return context.contentResolver.openInputStream(uri)?.use { input ->
            val exifInterface = ExifInterface(input)
            exifInterface.getAttributeInt(
                ExifInterface.TAG_ORIENTATION,
                ExifInterface.ORIENTATION_NORMAL
            )
        } ?: ExifInterface.ORIENTATION_NORMAL
    }

    companion object {
        private const val DEFAULT_QUALITY = 75
    }
}
