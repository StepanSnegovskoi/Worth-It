plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.core.ui"
}

dependencies {
    implementation(platform(libs.androidx.compose.bom))

    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui)

    androidTestImplementation(libs.androidx.junit)
}