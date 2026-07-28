plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.metes.worthit.core.navigation"
}

dependencies {
    implementation(libs.navigation.compose)
    androidTestImplementation(libs.androidx.junit)
}