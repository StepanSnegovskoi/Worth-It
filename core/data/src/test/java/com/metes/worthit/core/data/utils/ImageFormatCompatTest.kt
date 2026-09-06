package com.metes.worthit.core.data.utils

import android.graphics.Bitmap
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
class ImageFormatCompatTest {

    @Test
    @Config(maxSdk = 29)
    fun `ImageFormatCompat returns WEBP for API 29 and below`() {
        assertEquals(Bitmap.CompressFormat.WEBP, ImageFormatCompat.webpLossy)
    }

    @Test
    @Config(minSdk = 30)
    fun `ImageFormatCompat returns WEBP_LOSSY for API 30 and above`() {
        assertEquals(Bitmap.CompressFormat.WEBP_LOSSY, ImageFormatCompat.webpLossy)
    }
}
