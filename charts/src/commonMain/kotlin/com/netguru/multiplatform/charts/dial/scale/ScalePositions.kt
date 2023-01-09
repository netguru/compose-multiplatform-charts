package com.netguru.multiplatform.charts.dial.scale

import androidx.compose.ui.geometry.Offset

data class ScalePositions(
    val containerWidth: Float,
    val containerCenterX: Float,
    val scaleItems: List<ScaleItem>,
) {
    sealed class ScaleItem(
        val angle: Float,
        val showLabel: Boolean,
        val value: Float,
    ) {
        class Dot(
            angle: Float,
            showLabel: Boolean,
            value: Float,
            val offset: Offset,
        ) : ScaleItem(angle, showLabel, value)

        class Line(
            angle: Float,
            showLabel: Boolean,
            value: Float,
            val startOffset: Offset,
            val endOffset: Offset,
        ) : ScaleItem(angle, showLabel, value)
    }

    fun calculatedFor(width: Float, centerX: Float): Boolean {
        return containerWidth == width && containerCenterX == centerX
    }

    override fun equals(other: Any?): Boolean {
        other as ScalePositions

        return this.containerWidth == other.containerWidth && this.containerCenterX == other.containerCenterX
    }

    override fun hashCode(): Int {
        return containerWidth.hashCode() + containerCenterX.hashCode()
    }
}
