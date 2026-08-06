plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.metes.worthit.core.navigation"
}

dependencies {
    api(libs.navigation.compose)
    implementation(libs.javax.inject)

    implementation(project(":core:common"))

    androidTestImplementation(libs.androidx.junit)
}