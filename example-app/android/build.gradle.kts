import com.netguru.extensions.baseAndroidSetup

baseAndroidSetup()
plugins {
    kotlin("android")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.compose)
    id("com.android.application")
}

group = libs.versions.project.group.get()
version = libs.versions.project.version.get()

dependencies {
    implementation(libs.androidx.compose)
    debugImplementation(compose.uiTooling)
    implementation(compose.preview)
    implementation(libs.androidx.window)
    implementation(project(":example-app:application"))
}

android {
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    defaultConfig {
        applicationId = libs.versions.applicationId.get()
    }
}
