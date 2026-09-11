plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.datastore"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:common"))

    implementation(libs.datastore)
    testImplementation(libs.robolectric)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.androidx.junit)
}