package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.ClassName
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinMultiplatformPluginWrapper
import java.io.File
import javax.xml.parsers.DocumentBuilderFactory

const val packageName = "com.netguru.multiplatform.charts.common"
internal val immutableAnnotation = ClassName("androidx.compose.runtime", "Immutable")

abstract class ResourceGeneratorTask : DefaultTask() {
    private val stringResources = StringResources(packageName)
    private val colorResources = ColorResources(packageName)
    private val dimenResources = DimenResources(packageName)
    private val drawableResource = DrawableResources(packageName)
    private val resourcesWrapperClass = ResourcesWrapper(packageName)
    private val file = File(project.buildDir, "generated/resources")

    @TaskAction
    fun generateResources() {
        project.plugins.withType(KotlinMultiplatformPluginWrapper::class) {
            val multiplatformExt = project.extensions.getByType(KotlinMultiplatformExtension::class)
            val commonSourceSet = multiplatformExt.sourceSets.getByName("commonMain")
            val resources = commonSourceSet.resources.matching { include("/**/*.*") }

            readResources(resources.files)

            resourcesWrapperClass.create(stringResources, drawableResource).writeTo(file)
        }
    }

    private fun readResources(files: Set<File>) = files
        .forEach {
            try {
                val fileName = it.name.substringAfterLast("/").substringBeforeLast("_")
                val directoryName = it.toString().substringBeforeLast("/").substringAfterLast("/")
                when {
                    fileName == "strings" -> {
                        val language = it.name.substringAfterLast("_").substringBefore(".")
                        val elements = readXml("string", it)
                        stringResources.setStrings(language, elements)
                    }
                    fileName == "colors" -> {
                        colorResources.setColors(readXml("color", it))
                    }
                    fileName == "dimens" -> {
                        val windowSize = it.name.substringAfterLast("_").substringBefore(".")
                        val elements = readXml("dimen", it)
                        dimenResources.setDimens(windowSize, elements)
                    }
                    directoryName == "drawable" -> {
                        drawableResource.setDrawable(readFileName(it))

                    }
                }
            } catch (e: Exception) {
                println("error during parsing $e")
            }
        }

    private fun readXml(type: String, file: File): MutableMap<String, String> {
        val document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file)
        val elements = document.getElementsByTagName(type)
        val parsedElements = mutableMapOf<String, String>()
        for (elementIndex in 0 until elements.length) {
            val lineContent = elements.item(elementIndex)
            val name = lineContent.attributes.getNamedItem("name").textContent
            val value = lineContent.textContent.replace("\\", "")
            parsedElements[name] = value
        }
        return parsedElements
    }

    private fun readFileName(file: File): Pair<String, String> {
        val fileExtension = file.name.substringAfterLast("/")
        val fileName = fileExtension.substringBeforeLast(".")
        return fileName to fileExtension
    }
}
