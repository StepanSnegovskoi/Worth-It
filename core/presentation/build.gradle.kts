plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.core.presentation"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:ui"))
    implementation(project(":core:designsystem"))
    androidTestImplementation(libs.androidx.junit)
}