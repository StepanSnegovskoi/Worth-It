plugins {
    alias(libs.plugins.worthit.android.library)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit.core.datastore"
}

dependencies {
    implementation(project(":core:domain"))

    implementation(libs.datastore)
    androidTestImplementation(libs.androidx.junit)
}