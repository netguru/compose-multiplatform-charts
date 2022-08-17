import com.netguru.multiplatform.charts.extensions.androidMain
import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.baseTestSetup
import com.netguru.multiplatform.charts.extensions.commonMain
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
