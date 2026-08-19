plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.android.library.compose)
    alias(libs.plugins.serialization)
}

android {
    namespace = "com.metes.worthit.core.navigation"
}

dependencies {
    api(libs.androidx.navigation3.ui)
    api(libs.androidx.navigation3.runtime)
    api(libs.androidx.lifecycle.viewmodel.navigation3)
    api(libs.androidx.hilt.navigation.compose)
    implementation(libs.javax.inject)

    implementation(project(":core:common"))

    androidTestImplementation(libs.androidx.junit)
}