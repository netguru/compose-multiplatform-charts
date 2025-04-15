import com.netguru.multiplatform.charts.extensions.androidMain
import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.baseTestSetup
import com.netguru.multiplatform.charts.extensions.commonMain

baseAndroidSetup()
baseTestSetup()

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.compose)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                api(project(":example-app:common"))
            }
        }
        androidMain {
            dependencies {
                api(libs.compose.uiTooling)
            }
        }
    }
}
android {
    namespace = "com.netguru.multiplatform.charts.application"
}
