plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.room3)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.database"
}

dependencies {
    implementation(project(":core:domain"))
    androidTestImplementation(libs.androidx.junit)
}