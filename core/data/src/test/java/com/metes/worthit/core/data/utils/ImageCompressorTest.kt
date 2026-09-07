package com.metes.worthit.core.data.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.test.core.app.ApplicationProvider
import com.metes.worthit.core.domain.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class ImageCompressorTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var context: Context
    private lateinit var compressor: ImageCompressor

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        compressor = ImageCompressor(
            ioDispatcher = testDispatcher,
            context = context,
        )
    }

    @After
    fun teardown() {
        context.cacheDir.deleteRecursively()
    }

    @Test
    @Config(maxSdk = 29)
    fun `compress correctly scales down image`() = runTest(testDispatcher) {
        val image = createDummyFile("default_image.png", 2000, 2000)
        val outputFile = createDummyFile("output_image.png", 2000, 2000)
        val inputUri = Uri.fromFile(image)

        val result = compressor.compress(
            imageUri = inputUri,
            outputFile = outputFile,
            reqWidth = 500,
            reqHeight = 500,
            quality = 75,
            compressFormat = ImageFormatCompat.webpLossy
        )

        assertTrue(result is Result.Success)
        assertTrue(outputFile.exists())

        val options = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeFile(outputFile.absolutePath, options)

        assertEquals(500, options.outWidth)
        assertEquals(500, options.outHeight)
    }

    @Test
    fun `compress applies EXIF rotation correctly`() = runTest(testDispatcher) {
        val inputUri = copyResourceToCache("rotation_test_image.jpg")
        val outputFile = File(context.cacheDir, "rotated_test_image.jpg")

        val result = compressor.compress(
            imageUri = inputUri,
            outputFile = outputFile,
            reqWidth = 4080,
            reqHeight = 3072,
            quality = 75,
            compressFormat = ImageFormatCompat.webpLossy
        )

        assertTrue(result is Result.Success)
        assertTrue(outputFile.exists())

        val option = BitmapFactory.Options().apply {
            inJustDecodeBounds = true
        }

        BitmapFactory.decodeFile(outputFile.absolutePath, option)
        assertEquals(3072, option.outWidth)
        assertEquals(4080, option.outHeight)
    }

    private fun createDummyFile(fileName: String, width: Int, height: Int): File {
        val file = File(context.cacheDir, fileName)
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        file.outputStream().use { output ->
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output)
        }
        bitmap.recycle()

        return file
    }

    private fun copyResourceToCache(fileName: String): Uri {
        val resourceStream = javaClass.classLoader?.getResourceAsStream(fileName)
            ?: error("Test resource file $fileName not found in src/test/resources/")

        val tempFile = File(context.cacheDir, fileName)
        tempFile.outputStream().use { output ->
            resourceStream.copyTo(output)
        }

        return Uri.fromFile(tempFile)
    }
}