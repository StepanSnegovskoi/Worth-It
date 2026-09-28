plugins {
    alias(libs.plugins.worthit.android.feature)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.feature.items"
}

dependencies {
    androidTestImplementation(libs.androidx.junit)
    testImplementation(project(":core:testing"))
    testImplementation(libs.mockk)
    testImplementation(libs.turbine)

}