package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.*

class ColorResources(packageName: String) : Resources {
    private val composableAnnotation = ClassName("androidx.compose.runtime", "Composable")
    private val colorResourcesClass = ClassName(packageName, "ColorResources")
    private val colorSets = mutableMapOf<String, String>()
    private val colorClass = ClassName("androidx.compose.ui.graphics", "Color")

    fun setColors(colors: Map<String, String>) {
        colorSets.putAll(colors)
    }

    override fun addToWrapper(wrapper: TypeSpec.Builder) = with(wrapper) {
        addFunction(createGetColorMethod())
        addType(createColorResourceClass())
    }

    override fun addToFile(file: FileSpec.Builder) = with(file) {
        addFunction(createGetColorsMethod())
        addFunction(createGetColorsComposable())
    }

    private fun createGetColorMethod() = FunSpec.builder("getColors")
        .addStatement("return ColorResources")
        .build()

    private fun createColorResourceClass() = TypeSpec.objectBuilder(colorResourcesClass)
        .apply {
            colorSets
                .forEach { color -> addProperty(createColorProperty(color)) }
        }.build()

    private fun createColorProperty(color: Map.Entry<String, Any>): PropertySpec {
        val value = color.value
        val valueString = if (value is ArrayList<*>) {
            value.joinToString()
        } else {
            value
        }
        return PropertySpec.builder(
            color.key,
            colorClass
        ).initializer("Color($valueString)").build()
    }

    private fun createGetColorsMethod() = FunSpec.builder("getColors")
        .apply {
            addStatement("return Resources.ColorResources")
        }.build()

    private fun createGetColorsComposable() = FunSpec.builder("getColorsComposable")
        .addAnnotation(composableAnnotation)
        .apply {
            addStatement("return LocalResources.current.getColors()")
        }.build()
}
