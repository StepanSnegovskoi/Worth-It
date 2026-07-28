plugins {
    alias(libs.plugins.worthit.android.feature)
    alias(libs.plugins.worthit.android.library.compose)
}

android {
    namespace = "com.metes.worthit.feature.add_item"
}

dependencies {
    implementation(project(":core:data"))
    implementation(project(":core:datastore"))

    implementation(libs.androidx.activity.compose)
    androidTestImplementation(libs.androidx.junit)
}