package com.netguru.multiplatform.charts.resources

import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec

interface Resources {
    fun addToWrapper(wrapper: TypeSpec.Builder): TypeSpec.Builder
    fun addToFile(file: FileSpec.Builder): FileSpec.Builder
}
