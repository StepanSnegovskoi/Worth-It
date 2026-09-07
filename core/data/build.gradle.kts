plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.data"

    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.all { testTask ->
            testTask.maxHeapSize = "4G"
        }
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
    testImplementation(libs.robolectric)
    testImplementation(libs.androidx.junit)
}