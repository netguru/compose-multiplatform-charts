package com.netguru.multiplatform.charts.dial.scale

import androidx.compose.ui.geometry.Offset

data class ScalePositions(
    val containerWidth: Float,
    val containerCenterX: Float,
    val offsets: List<ScaleItem>,
) {
    sealed class ScaleItem(
        val angle: Float,
    ) {
        class Dot(
            angle: Float,
            val offset: Offset,
        ) : ScaleItem(angle)

        class Line(
            angle: Float,
            val startOffset: Offset,
            val endOffset: Offset,
        ) : ScaleItem(angle)
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
