plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.data"
}

dependencies {
    api(project(":core:domain"))
    api(project(":core:common"))
    implementation(project(":core:database"))

    implementation(libs.androidx.exifinterface)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}