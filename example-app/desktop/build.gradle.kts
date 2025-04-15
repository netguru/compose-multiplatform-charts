import com.netguru.multiplatform.charts.extensions.jvmMain
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.compose.desktop)
}

group = libs.versions.project.group.get()
version = libs.versions.project.version.get()

kotlin {
    jvm()
    sourceSets {
        jvmMain {
            dependencies {
                implementation(compose.desktop.currentOs)
                implementation(project(":example-app:application"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = libs.versions.desktop.packageName.get()
            packageVersion = libs.versions.desktop.packageVersion.get()
        }
    }
}
