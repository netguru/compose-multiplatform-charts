import com.netguru.extensions.androidMain
import com.netguru.extensions.baseAndroidSetup
import com.netguru.extensions.baseTestSetup
import com.netguru.extensions.commonMain
import org.jetbrains.compose.compose

baseAndroidSetup()
baseTestSetup()

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.compose)
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                api(project(":example-app:common"))
            }
        }
        androidMain {
            dependencies {
                api(compose.uiTooling)
            }
        }
    }
}
