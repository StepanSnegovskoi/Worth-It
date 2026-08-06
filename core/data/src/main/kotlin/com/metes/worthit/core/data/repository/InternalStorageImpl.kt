package com.metes.worthit.core.data.repository

import android.content.Context
import androidx.core.net.toUri
import com.metes.worthit.core.common.DispatcherProvider
import com.metes.worthit.core.data.utils.ImageCompressor
import com.metes.worthit.core.domain.error.BusinessError
import com.metes.worthit.core.domain.error.Error
import com.metes.worthit.core.domain.error.FileError
import com.metes.worthit.core.domain.error.UnexpectedError
import com.metes.worthit.core.domain.repository.StorageRepository
import com.metes.worthit.core.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dispatchers: DispatcherProvider,
    private val imageCompressor: ImageCompressor,
) : StorageRepository {

    override suspend fun saveImage(
        imagePath: String,
        fileName: String
    ): Result<String, Error> = withContext(dispatchers.io) {
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

                    if (result is Result.Success) {
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

    override suspend fun deleteFile(path: String): Result<Unit, Error> = withContext(dispatchers.io) {
        try {
            val file = File(path)
            if (file.exists() && !file.delete()) {
                return@withContext Result.Error(FileError.FailedToDeleteFile)
            }
            Result.Success(Unit)
        } catch (c: CancellationException) {
            throw c
        } catch (e: Exception) {
            Result.Error(UnexpectedError(e))
        }
    }

    private val internalStorageImagesDir by lazy {
        File(context.filesDir, IMAGES_DIR_NAME).apply { mkdirs() }
    }

    companion object {
        private const val IMAGES_DIR_NAME = "images"
    }
}
