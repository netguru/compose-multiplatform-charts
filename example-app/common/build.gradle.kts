import com.netguru.multiplatform.charts.extensions.androidMain
import com.netguru.multiplatform.charts.extensions.baseAndroidSetup
import com.netguru.multiplatform.charts.extensions.baseTestSetup
import com.netguru.multiplatform.charts.extensions.sourceSets
import org.gradle.kotlin.dsl.kotlin
import org.jetbrains.compose.compose

baseAndroidSetup()
baseTestSetup()

plugins {
    kotlin("multiplatform")
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.compose)
    id("com.android.library")
}

kotlin {
    android()
    jvm("desktop")

    sourceSets {
        commonMain {
            kotlin.srcDir("${buildDir.absolutePath}/generated/resources")
            dependencies {
                api(compose.runtime)
                api(compose.ui)
                api(compose.foundation)
                api(compose.material)
                api(compose.materialIconsExtended)
                api(libs.time.klock)
                api(project(":multiplatform-charts"))
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
