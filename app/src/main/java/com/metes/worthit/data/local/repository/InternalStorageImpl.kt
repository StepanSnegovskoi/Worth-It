package com.metes.worthit.data.local.repository

import android.content.Context
import androidx.core.net.toUri
import com.metes.worthit.app.StandardDispatchers
import com.metes.worthit.domain.repository.LocalMediaRepository
import com.metes.worthit.domain.repository.StorageRepository
import com.metes.worthit.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileNotFoundException
import java.time.Clock
import javax.inject.Inject
import javax.inject.Singleton

// String in 'StorageRepository<String, Result<String, Exception>>' is uri
@Singleton
class InternalStorageImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val dispatchers: StandardDispatchers,
    private val clock: Clock
) : LocalMediaRepository, StorageRepository<String, Result<String, Exception>> {

    override suspend fun saveImage(resourceIdentifier: String): Result<String, Exception> {
        return save(resourceIdentifier)
    }

    override suspend fun deleteImage(path: String) {
        return delete(path)
    }

    override suspend fun save(t: String): Result<String, Exception> = withContext(dispatchers.io) {
        try {
            val uri = t.toUri()
            val fileName = "IMG_${clock.millis()}.jpg"
            val imageFile = File(internalStorageImagesDir, fileName)

            with(context) {
                val inputStream =
                    contentResolver.openInputStream(uri) ?: throw FileNotFoundException(
                        "Unable to access file: $t"
                    )

                inputStream.use { inputStream ->
                    imageFile.outputStream().use { outputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
            }

            Result.Success(imageFile.path)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    override suspend fun delete(path: String) = withContext<Unit>(dispatchers.io) {
        File(path).delete()
    }

    private val internalStorageImagesDir by lazy {
        File(context.filesDir, IMAGES_DIR_NAME).apply { mkdirs() }
    }

    companion object {
        private const val IMAGES_DIR_NAME = "images"
    }
}