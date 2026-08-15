plugins {
    alias(libs.plugins.worthit.jvm.library)
}

dependencies {
    implementation(libs.javax.inject)
    testImplementation(libs.junit)
}