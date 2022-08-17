package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.*

class StringResources(packageName: String) : Resources {
    private val composableAnnotation = ClassName("androidx.compose.runtime", "Composable")
    private val readOnlyComposableAnnotation = ClassName("androidx.compose.runtime", "ReadOnlyComposable")
    private val stringResourceClass = ClassName(packageName, "StringResources")
    private val baseLanguageCode = "en"
    private val stringSets = mutableMapOf<String, Map<String, String>>()

    fun setStrings(language: String, strings: Map<String, String>) {
        stringSets[language] = strings
    }

    override fun addToWrapper(wrapper: TypeSpec.Builder) = with(wrapper) {
        addTypes(createLanguageClass())
        addFunction(createGetStringMethod())
    }

    override fun addToFile(file: FileSpec.Builder) = with(file) {
        addType(createStringResourceClass())
        addFunction(createGetStringHelper())
    }

    private fun createLanguageClass() = stringSets.map { createLanguageClass(it) }

    private fun createLanguageClass(language: Map.Entry<String, Map<String, String>>): TypeSpec {
        val languageCode = language.key.toUpperCase()
        val languagesProperties = if (language.key == baseLanguageCode) {
            emptyList<PropertySpec>()
        } else {
            language.value.toMap().map { it.createLanguageProperty() }
        }

        return TypeSpec.objectBuilder(languageCode)
            .superclass(stringResourceClass)
            .addAnnotation(immutableAnnotation)
            .addProperties(languagesProperties)
            .addModifiers(KModifier.PRIVATE)
            .build()
    }

    private fun Map.Entry<String, Any>.createLanguageProperty() =
        PropertySpec.builder(
            key.toLowerCase(),
            String::class,
            KModifier.OVERRIDE
        ).initializer("%S", value)
            .build()

    private fun createGetStringMethod() = FunSpec.builder("getString")
        .returns(stringResourceClass)
        .apply {
            addStatement("val language = when(locale.toUpperCase()) {")
            stringSets
                .filterNot { it.key.contains(baseLanguageCode) }
                .forEach {
                    val lang = it.key.substringAfterLast("_").toUpperCase()
                    addStatement("\"${lang}\" -> $lang")
                }
            addStatement("else -> ${baseLanguageCode.toUpperCase()}")
            addStatement("}")
            addStatement("return language")
        }.build()

    private fun createStringResourceClass() =
        TypeSpec
            .classBuilder(stringResourceClass)
            .addAnnotation(immutableAnnotation)
            .addModifiers(KModifier.ABSTRACT)
            .apply {
                stringSets[baseLanguageCode]
                    ?.toMap()
                    ?.forEach { addPropertyToStringResource(it) }
            }.build()

    private fun createGetStringHelper() = FunSpec.builder("getString")
        .returns(stringResourceClass)
        .addAnnotation(readOnlyComposableAnnotation)
        .addAnnotation(composableAnnotation)
        .apply {
            addStatement("return LocalResources.current.getString()")
        }.build()

    private fun TypeSpec.Builder.addPropertyToStringResource(language: Map.Entry<String, Any>) {
        val prop = PropertySpec.builder(
            language.key.toLowerCase(),
            String::class,
            KModifier.OPEN
        ).initializer("%S", language.value).build()
        addProperty(prop)
    }
}
