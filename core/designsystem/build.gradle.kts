plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.core.designsystem"
}

dependencies {
    api(platform(libs.androidx.compose.bom))
    api(libs.androidx.compose.ui)
    api(libs.androidx.compose.ui.graphics)
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.foundation.layout)
    api(libs.androidx.compose.runtime)
    api(libs.androidx.compose.material3)

    implementation(libs.coil.compose)
    androidTestImplementation(libs.androidx.junit)
}