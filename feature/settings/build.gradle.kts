plugins {
    alias(libs.plugins.worthit.android.feature)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.feature.settings"
}

dependencies {
    implementation(project(":core:data"))
    androidTestImplementation(libs.androidx.junit)
}