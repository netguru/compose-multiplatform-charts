package com.netguru.resources

import com.squareup.kotlinpoet.*

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
