package com.netguru.multiplatform.charts

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.layout.layout
import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.ceil
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sign

internal fun Double.mapValueToDifferentRange(
    inMin: Double,
    inMax: Double,
    outMin: Double,
    outMax: Double,
) =
    if (inMin == inMax) (outMax - outMin).div(2.0) else (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Float.mapValueToDifferentRange(
    inMin: Float,
    inMax: Float,
    outMin: Float,
    outMax: Float,
) =
    if (inMin == inMax) (outMax - outMin).div(2F) else (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Long.mapValueToDifferentRange(
    inMin: Long,
    inMax: Long,
    outMin: Long,
    outMax: Long,
) =
    if (inMin == inMax) (outMax - outMin).div(2) else (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

fun Long.mapValueToDifferentRange(
    inMin: Long,
    inMax: Long,
    outMin: Float,
    outMax: Float,
) =
    if (inMin == inMax) (outMax - outMin).div(2F) else (this - inMin) * (outMax - outMin) / (inMax - inMin) + outMin

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
fun getAnimationAlphas(
    animation: ChartDisplayAnimation,
    numberOfElementsToAnimate: Int,
    uniqueDatasetKey: Any,
): List<Float> {
    var animationPlayed by remember(uniqueDatasetKey) {
        mutableStateOf(animation is ChartDisplayAnimation.Disabled)
    }
    LaunchedEffect(uniqueDatasetKey) {
        animationPlayed = true
    }

    return when (animation) {
        ChartDisplayAnimation.Disabled -> (1..numberOfElementsToAnimate).map { 1f }
        is ChartDisplayAnimation.Simple -> (1..numberOfElementsToAnimate).map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = if (animationPlayed) animation.animationSpec() else tween(durationMillis = 0),
            ).value
        }

        is ChartDisplayAnimation.Sequenced -> (1..numberOfElementsToAnimate).map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = if (animationPlayed) animation.animationSpec(it) else tween(durationMillis = 0),
            ).value
        }
    }
}

fun Modifier.vertical() =
    rotate(-90f)
        .layout { measurable, constraints ->
            val placeable = measurable.measure(constraints)
            layout(placeable.height, placeable.width) {
                placeable.place(
                    x = -(placeable.width / 2 - placeable.height / 2),
                    y = -(placeable.height / 2 - placeable.width / 2)
                )
            }
        }

fun Float.toRadians() = this * PI.toFloat() / 180f

fun Float.roundToMultiplicationOf(multiplicand: Float, roundToCeiling: Boolean): Float {
    if (this == 0f) {
        return 0f
    }
    if (multiplicand <= 0) {
        throw IllegalArgumentException("multiplicand must be positive!")
    }

    fun Float.ceilOrFloor(ceil: Boolean): Float = if (ceil) {
        ceil(this)
    } else {
        floor(this)
    }

    val round = when (sign) {
        -1f -> !roundToCeiling
        1f -> roundToCeiling
        else -> throw IllegalStateException("If `this == 0f` this line should never be reached")
    }

    val closest = (absoluteValue / multiplicand).ceilOrFloor(round) * multiplicand

    return sign * closest
}
