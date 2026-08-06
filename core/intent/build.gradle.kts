plugins {
    alias(libs.plugins.worthit.android.library)
}

android {
    namespace = "com.metes.worthit.core.intent"
}

dependencies {
    implementation(libs.javax.inject)
    androidTestImplementation(libs.androidx.junit)
}