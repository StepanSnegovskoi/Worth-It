plugins {
    alias(libs.plugins.worthit.android.feature)
}

android {
    namespace = "com.metes.worthit.feature.share"
}

dependencies {
    implementation(project(":core:intent"))
    implementation(project(":core:navigation"))

    androidTestImplementation(libs.androidx.junit)
}