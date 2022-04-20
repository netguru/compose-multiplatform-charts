package com.netguru.extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the

fun Project.baseTestSetup() {
    kotlin {
        val libs = project.the<LibrariesForLibs>()
        sourceSets {
            commonTest {
                dependencies {
                    api(libs.test.coroutines)
                    implementation(libs.test.turbine)
                    implementation(libs.mockk.common)
                    implementation(kotlin("test"))
                }
            }
            androidTest {
                dependencies {
                    implementation(libs.test.junit)
                    implementation(libs.mockk)
                    implementation(libs.mockk.agentJvm)
                }
            }
            desktopTest {
                dependencies {
                    implementation(libs.mockk)
                }
            }
        }
    }
}
