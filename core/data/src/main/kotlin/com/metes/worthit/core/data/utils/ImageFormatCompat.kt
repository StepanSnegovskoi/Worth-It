package com.metes.worthit.core.data.utils

import android.graphics.Bitmap
import android.os.Build

object ImageFormatCompat {

    val webpLossy: Bitmap.CompressFormat
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                Bitmap.CompressFormat.WEBP_LOSSY
            } else {
                Bitmap.CompressFormat.WEBP
            }
}
