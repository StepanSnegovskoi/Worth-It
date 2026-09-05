plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.data"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    api(project(":core:domain"))
    api(project(":core:common"))
    implementation(project(":core:database"))

    implementation(libs.androidx.exifinterface)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.turbine)
}