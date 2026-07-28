plugins {
    alias(libs.plugins.worthit.jvm.library)
}

dependencies {
    api(libs.coroutines.core)
    api(libs.javax.inject)
    testImplementation(libs.junit)
}