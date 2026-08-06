plugins {
    alias(libs.plugins.worthit.jvm.library)
    alias(libs.plugins.worthit.hilt)
}

dependencies {
    api(libs.coroutines.core)
    testImplementation(libs.junit)
}