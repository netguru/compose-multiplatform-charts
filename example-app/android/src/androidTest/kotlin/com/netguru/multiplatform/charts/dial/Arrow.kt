package com.netguru.multiplatform.charts.dial

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val Arrow: ImageVector
    get() {
        if (_arrow != null) {
            return _arrow!!
        }
        val width = 30f
        val height = 30f
        val strokeLineWidth = 2f
        val strokeHalf = strokeLineWidth / 2
        _arrow = Builder(
            name = "Arrow",
            defaultWidth = width.dp,
            defaultHeight = height.dp,
            viewportWidth = width,
            viewportHeight = height
        ).apply {
            path(
                fill = null,
                stroke = SolidColor(Color.White),
                strokeLineWidth = strokeLineWidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                pathFillType = EvenOdd,
            ) {
                moveTo(width / 2, strokeHalf)
                lineTo(width - strokeHalf, height - strokeHalf)
            }

            path(
                fill = null,
                stroke = SolidColor(Color.White),
                strokeLineWidth = strokeLineWidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                pathFillType = EvenOdd,
            ) {
                moveTo(width - strokeHalf, height - strokeHalf)
                horizontalLineTo(strokeHalf)
            }

            path(
                fill = null,
                stroke = SolidColor(Color.White),
                strokeLineWidth = strokeLineWidth,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                pathFillType = EvenOdd,
            ) {
                moveTo(strokeHalf, height - strokeHalf)
                lineTo(width / 2, strokeHalf)
            }

            path(
                fill = SolidColor(Color.White),
                stroke = null,
                pathFillType = EvenOdd,
            ) {
                moveTo(width / 2, strokeHalf)
                lineTo(width - strokeHalf, height - strokeHalf)
                horizontalLineTo(strokeHalf)
                lineTo(width / 2, strokeHalf)
                close()
            }
        }
            .build()
        return _arrow!!
    }

private var _arrow: ImageVector? = null
