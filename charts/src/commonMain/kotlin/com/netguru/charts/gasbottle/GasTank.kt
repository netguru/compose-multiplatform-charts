package com.netguru.charts.gasbottle

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush.Companion.radialGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType.Companion.EvenOdd
import androidx.compose.ui.graphics.PathFillType.Companion.NonZero
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap.Companion.Butt
import androidx.compose.ui.graphics.StrokeJoin.Companion.Miter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.ImageVector.Builder
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

internal val GasTank: ImageVector
    get() {
        if (_bottle != null) {
            return _bottle!!
        }
        _bottle = Builder(
            name = "Bottle", defaultWidth = 85.0.dp, defaultHeight = 143.0.dp,
            viewportWidth = 85.0f, viewportHeight = 143.0f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFE9EAEC)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = EvenOdd
            ) {
                moveTo(42.5f, 132.291f)
                curveTo(62.451f, 132.291f, 78.625f, 129.698f, 78.625f, 126.939f)
                verticalLineTo(137.648f)
                curveTo(78.625f, 140.407f, 62.451f, 143.0f, 42.5f, 143.0f)
                curveTo(22.549f, 143.0f, 6.375f, 140.407f, 6.375f, 137.648f)
                verticalLineTo(126.939f)
                curveTo(6.375f, 129.698f, 22.549f, 132.291f, 42.5f, 132.291f)
                close()
                moveTo(65.993f, 135.373f)
                curveTo(65.445f, 135.44f, 65.055f, 135.939f, 65.122f, 136.488f)
                curveTo(65.19f, 137.036f, 65.689f, 137.426f, 66.237f, 137.358f)
                lineTo(69.215f, 136.993f)
                curveTo(69.763f, 136.925f, 70.153f, 136.426f, 70.085f, 135.878f)
                curveTo(70.018f, 135.33f, 69.519f, 134.94f, 68.971f, 135.008f)
                lineTo(65.993f, 135.373f)
                close()
                moveTo(42.001f, 136.795f)
                curveTo(41.448f, 136.795f, 41.001f, 137.243f, 41.001f, 137.795f)
                curveTo(41.001f, 138.347f, 41.448f, 138.795f, 42.001f, 138.795f)
                horizontalLineTo(45.001f)
                curveTo(45.553f, 138.795f, 46.001f, 138.347f, 46.001f, 137.795f)
                curveTo(46.001f, 137.243f, 45.553f, 136.795f, 45.001f, 136.795f)
                horizontalLineTo(42.001f)
                close()
                moveTo(22.078f, 136.49f)
                curveTo(22.135f, 135.94f, 21.737f, 135.448f, 21.188f, 135.39f)
                lineTo(18.204f, 135.077f)
                curveTo(17.655f, 135.019f, 17.163f, 135.418f, 17.105f, 135.967f)
                curveTo(17.047f, 136.516f, 17.446f, 137.008f, 17.995f, 137.066f)
                lineTo(20.979f, 137.38f)
                curveTo(21.528f, 137.437f, 22.02f, 137.039f, 22.078f, 136.49f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE0E1E3)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(78.625f, 126.939f)
                curveTo(78.625f, 129.698f, 62.451f, 132.291f, 42.5f, 132.291f)
                curveTo(22.549f, 132.291f, 6.375f, 129.698f, 6.375f, 126.939f)
                curveTo(6.375f, 124.18f, 22.549f, 121.943f, 42.5f, 121.943f)
                curveTo(62.451f, 121.943f, 78.625f, 124.18f, 78.625f, 126.939f)
                close()
            }
            path(
                fill = radialGradient(
                    0.0f to Color(0xFFFFFFFF), 1.0f to Color(0xFFE9EAEC),
                    center =
                    Offset(57.7292f, 51.6651f),
                    radius = 58.192f
                ),
                stroke = null,
                strokeLineWidth =
                0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(18.417f, 23.477f)
                curveTo(31.521f, 19.909f, 53.479f, 19.909f, 66.583f, 23.477f)
                curveTo(79.688f, 27.045f, 85.0f, 37.749f, 85.0f, 43.815f)
                curveTo(85.0f, 47.987f, 85.0f, 86.081f, 85.0f, 109.46f)
                curveTo(85.0f, 119.774f, 77.158f, 128.406f, 66.874f, 129.198f)
                curveTo(58.157f, 129.87f, 48.354f, 130.507f, 42.5f, 130.507f)
                curveTo(36.646f, 130.507f, 26.843f, 129.87f, 18.126f, 129.198f)
                curveTo(7.842f, 128.406f, 0.0f, 119.778f, 0.0f, 109.464f)
                curveTo(0.0f, 85.935f, 0.0f, 47.504f, 0.0f, 43.815f)
                curveTo(0.0f, 38.463f, 5.313f, 27.045f, 18.417f, 23.477f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE9EAEC)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(58.562f, 6.192f)
                curveTo(64.604f, 5.506f, 68.487f, 4.461f, 68.487f, 3.379f)
                curveTo(68.487f, 1.513f, 56.933f, 0.0f, 42.68f, 0.0f)
                curveTo(28.427f, 0.0f, 16.873f, 1.513f, 16.873f, 3.379f)
                curveTo(16.873f, 4.401f, 20.335f, 5.389f, 25.806f, 6.074f)
                verticalLineTo(24.5f)
                horizontalLineTo(58.562f)
                verticalLineTo(6.192f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE1E1E3)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(16.889f, 3.497f)
                lineTo(16.874f, 3.495f)
                verticalLineTo(23.5f)
                curveTo(16.874f, 27.411f, 19.86f, 28.606f, 23.509f, 29.192f)
                curveTo(27.159f, 29.779f, 29.624f, 29.606f, 29.624f, 29.606f)
                verticalLineTo(6.473f)
                curveTo(22.237f, 5.836f, 17.201f, 4.697f, 16.889f, 3.497f)
                close()
            }
            path(
                fill = SolidColor(Color(0xFFE1E1E3)), stroke = null, strokeLineWidth = 0.0f,
                strokeLineCap = Butt, strokeLineJoin = Miter, strokeLineMiter = 4.0f,
                pathFillType = NonZero
            ) {
                moveTo(55.738f, 6.473f)
                verticalLineTo(29.606f)
                curveTo(55.738f, 29.606f, 58.203f, 29.779f, 61.853f, 29.192f)
                curveTo(65.503f, 28.606f, 68.488f, 27.411f, 68.488f, 23.5f)
                verticalLineTo(3.495f)
                lineTo(68.473f, 3.497f)
                curveTo(68.161f, 4.697f, 63.125f, 5.836f, 55.738f, 6.473f)
                close()
            }
        }
            .build()
        return _bottle!!
    }

private var _bottle: ImageVector? = null
