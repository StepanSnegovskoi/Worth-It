plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    api(libs.coroutines.core)
    api(libs.javax.inject)
}

kotlin {
    jvmToolchain(17)
}