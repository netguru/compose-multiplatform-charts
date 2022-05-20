package com.netguru.charts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlin.math.pow
import kotlin.math.roundToInt

internal fun Double.mapValueToDifferentRange(
    inMin: Double,
    inMax: Double,
    outMin: Double,
    outMax: Double,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Long.mapValueToDifferentRange(
    inMin: Long,
    inMax: Long,
    outMin: Long,
    outMax: Long,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Long.mapValueToDifferentRange(
    inMin: Long,
    inMax: Long,
    outMin: Float,
    outMax: Float,
) = (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Number.round(decimals: Int = 2): String {
    return when (this) {
        is Double,
        is Float,
        -> try {
            ((this.toDouble() * 10.0.pow(decimals)).roundToInt() / 10.0.pow(decimals)).toString()
        } catch (e: IllegalArgumentException) {
            "-"
        }
        else -> {
            this.toString()
        }
    }
}

@Composable
internal fun StartAnimation(animation: ChartAnimation, data: Any): Boolean {
    var animationPlayed by remember(data) {
        mutableStateOf(animation is ChartAnimation.Disabled)
    }
    LaunchedEffect(Unit) {
        animationPlayed = true
    }

    return animationPlayed
}
