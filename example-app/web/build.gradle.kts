plugins {
    kotlin("multiplatform")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.compose)
}

group = libs.versions.project.group.get()
version = libs.versions.project.version.get()

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    google()
}

kotlin {
    js(IR) {
        browser()
        binaries.executable()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(compose.web.core)
                implementation(compose.runtime)
            }
        }
    }
}
