plugins {
    alias(libs.plugins.worthit.jvm.library)
}

dependencies {
    api(project(":core:domain"))
}