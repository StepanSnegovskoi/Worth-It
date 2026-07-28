import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.metes.worthit.convention"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.room.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = libs.plugins.worthit.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.worthit.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.worthit.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("androidFeature") {
            id = libs.plugins.worthit.android.feature.get().pluginId
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = libs.plugins.worthit.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.worthit.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("JvmLibrary") {
            id = libs.plugins.worthit.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("androidRoom") {
            id = libs.plugins.worthit.android.room.get().pluginId
            implementationClass = "AndroidRoomConventionPlugin"
        }
    }
}