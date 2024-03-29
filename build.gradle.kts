plugins {
    @Suppress("DSL_SCOPE_VIOLATION")
    alias(libs.plugins.ktlint)
}

buildscript {
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
    dependencies {
        classpath(libs.plugin.android)
        classpath(libs.plugin.kotlin)
        classpath(libs.plugin.shot)
    }
}

group = libs.versions.project.group.get()
version = libs.versions.project.version.get()

allprojects {
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)
    configure<org.jlleitschuh.gradle.ktlint.KtlintExtension> {
        filter {
            exclude { element -> element.file.path.contains("generated/") }
        }
    }

    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = libs.versions.jvmTarget.get()
            freeCompilerArgs = freeCompilerArgs + listOf(libs.versions.optInFlags.get())
        }
    }
}
