package com.metes.worthit.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.metes.worthit.core.common.IoDispatcher
import com.metes.worthit.core.data.utils.ImageCompressor
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import com.metes.worthit.core.domain.utils.onSuccess
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val imageCompressor: ImageCompressor,
) : StorageRepository {

    override suspend fun saveImage(
        imagePath: String,
        fileName: String
    ): Result<String, Error> = withContext(ioDispatcher) {
        val uri = imagePath.toUri()
        var imageFile: File? = null

        try {
            when (uri.scheme) {
                "file", null -> {
                    val file = File(imagePath)
                    if (file.exists()) {
                        Result.Success(imagePath)
                    } else {
                        Result.Error(FileError.FileNotFound)
                    }
                }

                "content" -> {
                    val fullFileName = "$fileName.webp"
                    imageFile = File(internalStorageImagesDir, fullFileName)

                    val result = imageCompressor.compress(uri, imageFile)

                    result.onSuccess {
                        return@withContext Result.Success(imageFile.path)
                    }

                    imageFile.delete()
                    Result.Error(FileError.CompressionFailed)
                }

                else -> Result.Error(FileError.UnsupportedUriScheme)
            }
        } catch (c: CancellationException) {
            imageFile?.delete()
            throw c
        } catch (_: Exception) {
            imageFile?.delete()
            Result.Error(BusinessError.ItemImageFailedToSave)
        }
    }

    override suspend fun deleteFile(path: String): Result<Unit, Error> = withContext(ioDispatcher) {
        try {
            val file = File(path)
            if (file.exists() && !file.delete()) {
                return@withContext Result.Error(FileError.FailedToDeleteFile)
            }
            Result.Success(Unit)
        } catch (c: CancellationException) {
            throw c
        } catch (_: Exception) {
            Result.Error(FileError.FailedToDeleteFile)
        }
    }

    private val internalStorageImagesDir by lazy {
        File(context.filesDir, IMAGES_DIR_NAME).apply { mkdirs() }
    }

    companion object {
        private const val IMAGES_DIR_NAME = "images"
    }
}
