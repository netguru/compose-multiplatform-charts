import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import kotlin.collections.listOf

baseAndroidSetup()
plugins {
    kotlin("android")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.compose)
    id("com.android.application")
    id("shot")
}

group = libs.versions.project.group.get()
version = libs.versions.project.version.get()

dependencies {
    implementation(libs.androidx.compose)
    debugImplementation(libs.compose.uiTooling)
    implementation(libs.compose.preview)
    implementation(libs.androidx.window)
    implementation(project(":example-app:application"))
    androidTestImplementation(libs.test.junit)
    androidTestImplementation(libs.test.ui.junit)
    debugImplementation(libs.test.ui.manifest)
}

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    defaultConfig {
        applicationId = libs.versions.applicationId.get()
        testInstrumentationRunner = "com.karumi.shot.ShotTestRunner"
    }

    packagingOptions {
        resources.excludes += listOf(
            "META-INF/AL2.0",
            "META-INF/LGPL2.1"
        )
    }
    namespace = "com.netguru.multiplatform.charts.android"
}

shot {
    tolerance = 2.0 // 2% tolerance, needed for testing on different devices
}
