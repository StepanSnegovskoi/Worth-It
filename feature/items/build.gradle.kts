plugins {
    alias(libs.plugins.worthit.android.feature)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.feature.items"
}

dependencies {
    implementation(project(":core:data"))
    androidTestImplementation(libs.androidx.junit)
}