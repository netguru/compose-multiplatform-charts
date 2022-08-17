package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.*

class DrawableResources(packageName: String) : Resources {
    private val composableAnnotation = ClassName("androidx.compose.runtime", "Composable")
    private val readOnlyComposableAnnotation =
        ClassName("androidx.compose.runtime", "ReadOnlyComposable")
    private val drawableResourcesClass = ClassName(packageName, "DrawableResources")
    private val drawableSets = mutableMapOf<String, String>()

    fun setDrawable(drawable: Pair<String, String>) {
        drawableSets[drawable.first] = drawable.second
    }

    override fun addToWrapper(wrapper: TypeSpec.Builder) = with(wrapper) {
        addFunction(createGetDrawableMethod())
    }

    override fun addToFile(file: FileSpec.Builder) = with(file) {
        addType(createDrawableResourceClass())
        addFunction(createGetDrawableHelper())
    }

    private fun createGetDrawableMethod() = FunSpec.builder("getDrawableResources")
        .addStatement("return DrawableResources")
        .build()

    private fun createGetDrawableHelper() = FunSpec.builder("getDrawbles")
        .returns(drawableResourcesClass)
        .addAnnotation(readOnlyComposableAnnotation)
        .addAnnotation(composableAnnotation)
        .apply {
            addStatement("return LocalResources.current.getDrawableResources()")
        }.build()

    private fun createDrawableResourceClass() = TypeSpec.objectBuilder(drawableResourcesClass)
        .addAnnotation(immutableAnnotation)
        .apply {
            drawableSets
                .forEach { drawable -> addProperty(createDrawableProperty(drawable)) }
        }.build()

    private fun createDrawableProperty(drawable: Map.Entry<String, Any>) =
        PropertySpec.builder(
            drawable.key.toLowerCase(),
            String::class
        ).initializer("%S", drawable.value)
            .build()
}
