plugins {
    alias(libs.plugins.worthit.jvm.library)
}

dependencies {
    api(project(":core:domain"))
    api(libs.kotlinx.coroutines.test)
    api(libs.junit)
}
