package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

class ResourcesWrapper(packageName: String) {
    private val resourceClassName = ClassName(packageName, "ResourcesImpl")
    private val resourceFileClassName = ClassName(packageName, "ResourceFile")

    private fun createResourceFile(apply: FileSpec.Builder.() -> FileSpec.Builder) =
        FileSpec.builder(resourceFileClassName.packageName, resourceFileClassName.simpleName).apply().build()

    fun create(vararg resources: Resources) =
        createResourceFile {
            addType(
                createResourceClass {
                    resources.forEach {
                        it.addToWrapper(this)
                    }
                    this
                }
            )
            resources.forEach {
                it.addToFile(this)
            }
            this
        }

    private fun createResourceClass(apply: TypeSpec.Builder.() -> TypeSpec.Builder) =
        TypeSpec.classBuilder(resourceClassName)
            .primaryConstructor(createResourceParam())
            .addAnnotation(immutableAnnotation)
            .addProperty(
                PropertySpec.builder("locale", String::class)
                    .initializer("locale")
                    .addModifiers(KModifier.PRIVATE)
                    .build()
            )
            .apply()
            .build()

    private fun createResourceParam() = FunSpec
        .constructorBuilder()
        .addParameter("locale", String::class)
        .build()
}
