plugins {
    alias(libs.plugins.worthit.android.feature)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.feature.settings"
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
}