package com.metes.worthit.data.local.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.graphics.BitmapCompat
import androidx.core.net.toUri
import com.metes.worthit.app.StandardDispatchers
import com.metes.worthit.data.utils.ImageCompressor
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.repository.StorageRepository
import com.metes.worthit.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.time.Clock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InternalStorageImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dispatchers: StandardDispatchers,
    private val clock: Clock,
    private val imageCompressor: ImageCompressor
) : LocalMediaRepository, StorageRepository<String, Result<String, Exception>> {

    override suspend fun saveImage(resourceIdentifier: String): Result<String, Exception> {
        return save(resourceIdentifier)
    }

    override suspend fun deleteImage(path: String): Result<Unit, Exception> {
        return delete(path)
    }

    override suspend fun save(t: String): Result<String, Exception> = withContext(dispatchers.io) {
        val uri = t.toUri()
        val fileName = "IMG_${clock.millis()}.webp"
        val imageFile = File(internalStorageImagesDir, fileName)

        try {
            imageFile.outputStream().use { outputStream ->
                val result = imageCompressor.compress(
                    uri,
                    outputStream
                )

                if (result is Result.Error) {
                    imageFile.delete()
                    return@withContext Result.Error(
                        Exception(
                            "Compression failed",
                            result.error
                        )
                    )
                }
            }

            Result.Success(imageFile.path)
        } catch (c: CancellationException) {
            imageFile.delete()
            throw c
        } catch (e: Exception) {
            imageFile.delete()
            Result.Error(e)
        }
    }

    override suspend fun delete(path: String): Result<Unit, Exception> =
        withContext(dispatchers.io) {
            try {
                val file = File(path)
                if (file.exists() && !file.delete()) {
                    return@withContext Result.Error(IOException("System failed to delete file: $path"))
                }
                Result.Success(Unit)
            } catch (c: CancellationException) {
                throw c
            } catch (e: Exception) {
                Result.Error(e)
            }
        }

    private val internalStorageImagesDir by lazy {
        File(context.filesDir, IMAGES_DIR_NAME).apply { mkdirs() }
    }

    companion object {
        private const val IMAGES_DIR_NAME = "images"
    }
}