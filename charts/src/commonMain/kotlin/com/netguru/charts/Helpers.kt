package com.netguru.charts

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

internal fun Double.mapValueToDifferentRange(
    inMin: Double,
    inMax: Double,
    outMin: Double,
    outMax: Double
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Long.mapValueToDifferentRange(
    inMin: Long,
    inMax: Long,
    outMin: Long,
    outMax: Long
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

data class PointF(val x: Float, val y: Float)

fun Float.round(decimals: Int = 2): String {
    val integerDigits = this.toInt()
    var floatDigits = ((this - integerDigits) * 10f.pow(decimals))
    if (floatDigits.isNaN()) floatDigits = 0f
    return "$integerDigits.${abs(floatDigits.roundToInt())}"
}
