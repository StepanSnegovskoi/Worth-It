plugins {
    alias(libs.plugins.worthit.jvm.library)
}

dependencies {
    api(libs.coroutines.core)
    implementation(libs.javax.inject)
}