plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.core.ui"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:designsystem"))

    androidTestImplementation(libs.androidx.junit)
}