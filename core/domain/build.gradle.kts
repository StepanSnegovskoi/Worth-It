plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

dependencies {
    implementation(libs.coroutines.core)
    implementation(libs.javax.inject)
}

kotlin {
    jvmToolchain(17)
}