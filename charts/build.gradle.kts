import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.commonMain
import com.netguru.multiplatform.charts.extensions.commonTest
import com.netguru.multiplatform.charts.extensions.kotlin
import com.netguru.multiplatform.charts.extensions.sourceSets
import org.jetbrains.compose.compose
import java.net.URL

baseAndroidSetup()

plugins {
    alias(libs.plugins.compose)
    kotlin("multiplatform")
    id("com.android.library")
    alias(libs.plugins.dokka)
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
