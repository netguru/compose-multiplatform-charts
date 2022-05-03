import com.netguru.extensions.baseAndroidSetup
import com.netguru.extensions.commonMain
import com.netguru.extensions.commonTest
import com.netguru.extensions.kotlin
import com.netguru.extensions.sourceSets
import org.jetbrains.compose.compose

baseAndroidSetup()

plugins {
    alias(libs.plugins.compose)
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
