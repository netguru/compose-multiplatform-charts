package com.netguru.multiplatform.charts.extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet

fun Project.android(block: BaseExtension.() -> Unit) {
    extensions.getByType<BaseExtension>().block()
}

fun Project.kotlin(block: KotlinMultiplatformExtension.() -> Unit) {
    extensions.getByType<KotlinMultiplatformExtension>().block()
}

fun Project.publishing(block: PublishingExtension.() -> Unit) {
    extensions.getByType<PublishingExtension>().block()
}

typealias SourceSets = NamedDomainObjectContainer<KotlinSourceSet>

fun KotlinMultiplatformExtension.sourceSets(block: SourceSets.() -> Unit) {
    sourceSets.block()
}

private fun SourceSets.getOrCreate(name: String): KotlinSourceSet = findByName(name) ?: create(name)

fun SourceSets.commonMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("commonMain").apply(block)

fun SourceSets.commonTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("commonTest").apply(block)

fun SourceSets.androidMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("androidMain").apply(block)

fun SourceSets.androidTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("androidTest").apply(block)

fun SourceSets.desktopMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("desktopMain").apply(block)

fun SourceSets.desktopTest(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("desktopTest").apply(block)

fun SourceSets.jvmMain(block: KotlinSourceSet.() -> Unit = {}): KotlinSourceSet =
    getOrCreate("jvmMain").apply(block)
