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
    implementation(project(":core:common"))
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.androidx.junit)
    testImplementation(libs.turbine)
}