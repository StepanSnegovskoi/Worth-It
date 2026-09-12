plugins {
    alias(libs.plugins.worthit.android.library)
}

android {
    namespace = "com.metes.worthit.core.intent"

    testOptions {
        unitTests.isReturnDefaultValues = true
    }
}

dependencies {
    implementation(libs.javax.inject)
    testImplementation(libs.mockk)
}