@file:Suppress("UnstableApiUsage")

plugins {
    alias(libs.plugins.worthit.android.application)
    alias(libs.plugins.worthit.android.application.compose)
    alias(libs.plugins.worthit.hilt)
}

android {
    namespace = "com.metes.worthit"

    defaultConfig {
        applicationId = "com.metes.worthit"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
        }
    }

    androidResources {
        generateLocaleConfig = true
    }
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:data"))
    implementation(project(":core:navigation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:datastore"))
    implementation(project(":core:common"))
    implementation(project(":core:intent"))
    implementation(project(":feature:items"))
    implementation(project(":feature:save_item"))
    implementation(project(":feature:settings"))
    implementation(project(":feature:share"))

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.core.splashscreen)

    androidTestImplementation(libs.androidx.junit)
}