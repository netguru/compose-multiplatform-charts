import com.netguru.multiplatform.charts.extensions.androidMain
import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.baseTestSetup
import com.netguru.multiplatform.charts.extensions.sourceSets
import org.gradle.kotlin.dsl.kotlin

baseAndroidSetup()
baseTestSetup()

plugins {
    kotlin("multiplatform")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.kotlin.compose)
    id("com.android.library")
}

kotlin {
    androidTarget()
    jvm("desktop")

    sourceSets {
        commonMain {
            kotlin.srcDir("${buildDir.absolutePath}/generated/resources")
            dependencies {
                api(libs.compose.runtime)
                api(libs.compose.ui)
                api(libs.compose.foundation)
                api(libs.compose.material)
                api(libs.compose.materialIconsExtended)
                api(libs.time.klock)
                api(project(":charts"))
            }
        }
        androidMain {
            dependencies {
                api(libs.androidx.appcompat)
                api(libs.androidx.coreKtx)
            }
        }
    }
}

android {
    sourceSets["main"].apply {
        res.srcDirs("src/androidMain/res", "src/commonMain/resources")
    }
    namespace = "com.netguru.multiplatform.charts.common"
}

project.rootProject.tasks.apply {
    register("resourceGeneratorTask", com.netguru.multiplatform.charts.resources.ResourceGeneratorTask::class) {
        group = "resource Generator"
        description = "Task for generating resources"
    }
    matching {
        it.name.contains("prepareKotlinBuildScriptModel")
    }.configureEach {
        dependsOn("resourceGeneratorTask")
    }
}

project.tasks.apply {
    register("resourceGeneratorTask", com.netguru.multiplatform.charts.resources.ResourceGeneratorTask::class) {
        group = "resource Generator"
        description = "Task for generating resources"
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        dependsOn("resourceGeneratorTask")
    }
}
