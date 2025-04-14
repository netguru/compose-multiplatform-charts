import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.commonMain
import com.netguru.multiplatform.charts.extensions.commonTest
import com.netguru.multiplatform.charts.extensions.kotlin
import com.netguru.multiplatform.charts.extensions.sourceSets
import java.net.URL

baseAndroidSetup()

plugins {
    alias(libs.plugins.kotlin.compose)
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.dokka)
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        commonMain {
            dependencies {
                api(libs.compose.runtime)
                api(libs.compose.ui)
                api(libs.compose.foundation)
                api(libs.compose.material)
                api(libs.compose.materialIconsExtended)
            }
        }
        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}
android {
    namespace = "com.netguru.multiplatform.charts"
}

tasks.withType<org.jetbrains.dokka.gradle.DokkaTask>().configureEach {
    dokkaSourceSets {
        named("commonMain") {
            moduleName.set("Kotlin multiplatform charts")
            sourceLink {
                val dir = "src/commonMain/kotlin"
                localDirectory.set(file(dir))
                remoteUrl.set(
                    URL(
                        "https://github.com/netguru/compose-multiplatform-charts/tree/main/charts/$dir"
                    )
                )
                remoteLineSuffix.set("#L")
            }
        }
    }
}
