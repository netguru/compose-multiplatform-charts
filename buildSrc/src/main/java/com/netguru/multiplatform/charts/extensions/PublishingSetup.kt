package com.netguru.multiplatform.charts.extensions

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.the
import java.util.*

fun Project.publishingSetup() {
    val libs = project.the<LibrariesForLibs>()

    val version = if (project.extra.has("libVersion")) {
        project.extra.get("libVersion")
            .let { it as String }
            .run { if (startsWith("v")) drop(1) else this }
    } else {
        libs.versions.project.version.get()
    }

    project.group = libs.versions.project.group.get()
    project.version = version

    val secretPropsFile = project.rootProject.file("local.properties")
    if (secretPropsFile.exists()) {
        secretPropsFile.reader().use {
            Properties().apply {
                load(it)
            }
        }.let { properties ->
            project.extra["signing.keyId"] = properties.getProperty("signing.keyId")
            project.extra["signing.password"] = properties.getProperty("signing.password")
            project.extra["signing.secretKeyRingFile"] = properties.getProperty("signing.secretKeyRingFile")
            project.extra["ossrhUsername"] = properties.getProperty("ossrhUsername")
            project.extra["ossrhPassword"] = properties.getProperty("ossrhPassword")
        }
    } else {
        project.extra["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
        project.extra["signing.password"] = System.getenv("SIGNING_PASSWORD")
        project.extra["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
        project.extra["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
        project.extra["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
    }

    publishing {
        repositories {
            maven {
                name = "sonatype"
                setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
                credentials {
                    username = project.extra["ossrhUsername"]?.toString()
                    password = project.extra["ossrhPassword"]?.toString()
                }
            }
        }

        publications.withType(MavenPublication::class.java) {
            pom {
                name.set(libs.versions.project.name.get())
                description.set(libs.versions.project.description.get())
                url.set(libs.versions.project.url.get())

                licenses {
                    license {
                        name.set("MIT")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }
                scm {
                    url.set(libs.versions.project.url.get())
                }
            }
        }
    }
}
