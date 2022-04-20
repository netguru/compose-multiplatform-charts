package com.netguru.resources

import com.squareup.kotlinpoet.*

class DimenResources(packageName: String) : Resources {
    private val baseDimen = "compact"
    private val dimenSets = mutableMapOf<String, Map<String, String>>()
    private val dimenResourceClass = ClassName(packageName, "DimenResource")
    private val composableAnnotation = ClassName("androidx.compose.runtime", "Composable")

    fun setDimens(windowSize: String, dimens: Map<String, String>) {
        dimenSets[windowSize] = dimens
    }

    override fun addToWrapper(wrapper: TypeSpec.Builder) = with(wrapper) {
        dimenSets.forEach { addType(createDimenResourcesClass(it)) }
        addFunction(createGetDimensMethod())
    }

    override fun addToFile(file: FileSpec.Builder) = with(file) {
        addType(createDimenResourcesClass())
        addFunction(createGetDimenComposable())
    }

    private fun createDimenResourcesClass(dimen: Map.Entry<String, Map<String, String>>): TypeSpec {
        val dimenName = dimen.key
        val dimenValue = if (dimen.key == baseDimen) {
            emptyList<PropertySpec>()
        } else {
            dimen.value.toMap().map { it.createDimenProperty() }
        }
        return TypeSpec.objectBuilder(dimenName)
            .superclass(dimenResourceClass)
            .addProperties(dimenValue)
            .addModifiers(KModifier.PRIVATE)
            .build()
    }

    private fun createDimenResourcesClass() = TypeSpec
        .classBuilder(dimenResourceClass)
        .addModifiers(KModifier.ABSTRACT)
        .apply {
            dimenSets[baseDimen]
                ?.toMap()
                ?.forEach { addPropertyToDimenResource(it) }
        }.build()

    private fun createGetDimensMethod() = FunSpec.builder("getDimens")
        .returns(dimenResourceClass)
        .apply {
            addStatement("val dimens = when(windowSize.toLowerCase()) {")
            dimenSets
                .filterNot { it.key.contains(baseDimen) }
                .forEach {
                    val windowSize = it.key.toLowerCase()
                    addStatement("\"${windowSize}\" -> $windowSize")
                }
            addStatement("else -> $baseDimen")
            addStatement("}")
            addStatement("return dimens")
        }.build()

    private fun TypeSpec.Builder.addPropertyToDimenResource(dimen: Map.Entry<String, Any>) {
        val prop = PropertySpec.builder(
            dimen.key,
            getNumericType(dimen.value),
            KModifier.OPEN
        ).initializer("%L", dimen.value).build()
        addProperty(prop)
    }

    private fun createGetDimenComposable() = FunSpec.builder("getDimens")
        .returns(dimenResourceClass)
        .addAnnotation(composableAnnotation)
        .apply {
            addStatement("return LocalResources.current.getDimens()")
        }.build()

    private fun Map.Entry<String, Any>.createDimenProperty() =
        PropertySpec.builder(
            key,
            getNumericType(value),
            KModifier.OVERRIDE
        ).initializer("%L", value)
            .build()

    private fun getNumericType(number: Any) =
        with(number.toString()) {
            when {
                contains("F") -> Float::class
                contains(".") -> Double::class
                else -> Int::class
            }
        }
}
