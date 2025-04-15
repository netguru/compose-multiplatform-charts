package com.netguru.multiplatform.charts.extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.the

fun Project.baseAndroidSetup() {
    android {
        val libs = project.the<LibrariesForLibs>()
        compileSdkVersion(libs.versions.compileSdk.get().toInt())
        if (project.plugins.hasPlugin("com.android.library")) {
            sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
        }
        if (project.plugins.hasPlugin(libs.plugins.kotlin.compose.get().pluginId)) {
            buildFeatures.compose = true
        }
        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            targetSdk = libs.versions.targetSdk.get().toInt()
            versionCode = BitriseUtil.getBuildVersionCode() ?: libs.versions.versionCode.get().toInt()
            versionName = libs.versions.versionName.get()
            testInstrumentationRunner = libs.versions.testInstrumentationRunner.get()
        }

        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
    }
}
