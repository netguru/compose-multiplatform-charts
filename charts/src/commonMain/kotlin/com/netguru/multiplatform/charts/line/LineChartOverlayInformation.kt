package com.netguru.multiplatform.charts.line

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.OverlayInformation
import com.netguru.multiplatform.charts.grid.axisscale.x.XAxisScale
import com.netguru.multiplatform.charts.mapValueToDifferentRange
import kotlin.math.abs

@Composable
internal fun LineChartTooltip(
    lineChartData: List<LineChartData>,
    positionX: Float,
    containerSize: Size,
    colors: LineChartColors,
    drawPoints: (points: List<SeriesAndClosestPoint>) -> Unit,
    tooltipConfig: TooltipConfig,
    xAxisScale: XAxisScale,
) {
    if (tooltipConfig.showInterpolatedValues) {
        LineChartOverlayInformationWithInterpolatedValues(
            lineChartData = lineChartData,
            positionX = positionX,
            containerSize = containerSize,
            colors = colors,
            overlayHeaderLayout = tooltipConfig.headerLabel,
            overlayDataEntryLayout = tooltipConfig.dataEntryLabel,
            touchOffsetHorizontal = tooltipConfig.touchOffsetHorizontal,
            touchOffsetVertical = tooltipConfig.touchOffsetVertical,
            overlayWidth = tooltipConfig.width,
            overlayAlpha = tooltipConfig.alpha,
            xAxisScale = xAxisScale,
        )
    } else {
        LineChartTooltip(
            lineChartData = lineChartData,
            positionX = positionX,
            containerSize = containerSize,
            colors = colors,
            overlayHeaderLayout = tooltipConfig.headerLabel,
            overlayDataEntryLayout = tooltipConfig.dataEntryLabel,
            drawPoints = drawPoints,
            highlightPointsCloserThan = tooltipConfig.highlightPointsCloserThan,
            touchOffsetHorizontal = tooltipConfig.touchOffsetHorizontal,
            touchOffsetVertical = tooltipConfig.touchOffsetVertical,
            overlayWidth = tooltipConfig.width,
            overlayAlpha = tooltipConfig.alpha,
            xAxisScale = xAxisScale,
        )
    }
}

@Composable
private fun LineChartTooltip(
    lineChartData: List<LineChartData>,
    xAxisScale: XAxisScale,
    positionX: Float,
    containerSize: Size,
    colors: LineChartColors,
    overlayHeaderLayout: @Composable (value: Any, dataUnit: String?) -> Unit,
    overlayDataEntryLayout: @Composable (dataName: String, dataNameShort: String?, dataUnit: String?, value: Any) -> Unit,
    drawPoints: (points: List<SeriesAndClosestPoint>) -> Unit,
    highlightPointsCloserThan: Dp,
    touchOffsetHorizontal: Dp,
    touchOffsetVertical: Dp,
    overlayWidth: Dp?,
    overlayAlpha: Float,
) {
    if (positionX < 0) {
        drawPoints(emptyList())
        return
    }

    val timestampCursor = getTimestampFromCursor(
        xCursorPosition = positionX,
        xAxisScale = xAxisScale,
        containerSize = containerSize
    )
    val listOfValues = retrieveDataWithClosestPointForEachSeries(lineChartData, timestampCursor)
    var linePositionX: Float? by remember {
        mutableStateOf(null)
    }

    if (listOfValues.isNotEmpty()) {

        val highlightPointsCloserThanPixels = with(LocalDensity.current) {
            highlightPointsCloserThan.toPx()
        }

        linePositionX =
            listOfValues.first().closestPoint.x.mapValueToDifferentRange(
                inMin = xAxisScale.start,
                inMax = xAxisScale.end,
                outMin = 0f,
                outMax = containerSize.width,
            )

        val (pointsToAvoid, valuesToShowDataFor) = listOfValues.mapNotNull {
            val (x, y) = it.closestPoint.let { point ->
                point.x.mapValueToDifferentRange(
                    inMin = xAxisScale.start,
                    inMax = xAxisScale.end,
                    outMin = 0f,
                    outMax = containerSize.width,
                ) to point.y!!.mapValueToDifferentRange(
                    inMin = it.lineChartSeries.minValue,
                    inMax = it.lineChartSeries.maxValue,
                    outMin = 0f,
                    outMax = containerSize.height,
                )
            }

            if (abs(x - linePositionX!!) <= highlightPointsCloserThanPixels) {
                Offset(x, y) to it
            } else {
                null
            }
        }
            .unzip()

        drawPoints(valuesToShowDataFor)

        OverlayInformation(
            positionX = linePositionX,
            containerSize = containerSize,
            backgroundColor = colors.overlaySurface,
            pointsToAvoid = pointsToAvoid,
            touchOffsetHorizontal = touchOffsetHorizontal,
            touchOffsetVertical = touchOffsetVertical,
            requiredOverlayWidth = overlayWidth,
            overlayAlpha = overlayAlpha,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                valuesToShowDataFor
                    .groupBy { it.closestPoint.x }
                    .forEach { closestPointBySeries ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            overlayHeaderLayout(closestPointBySeries.key, null)

                            Spacer(modifier = Modifier.height(4.dp))

                            closestPointBySeries.value
                                .forEach { seriesAndClosestPoint ->
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .align(Alignment.Start)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(16.dp, 4.dp)
                                                .drawBehind {
                                                    drawLine(
                                                        strokeWidth = seriesAndClosestPoint.lineChartSeries.lineWidth.toPx(),
                                                        pathEffect = seriesAndClosestPoint.lineChartSeries.pathEffect,
                                                        color = seriesAndClosestPoint.lineChartSeries.lineColor,
                                                        start = Offset(0f, size.height / 2),
                                                        end = Offset(size.width, size.height / 2)
                                                    )
                                                }
                                        )

                                        Spacer(modifier = Modifier.width(8.dp))

                                        val dataName = seriesAndClosestPoint.lineChartSeries.dataName
                                        val dataNameShort = seriesAndClosestPoint.lineChartSeries.dataNameShort
                                        val dataUnit = seriesAndClosestPoint.dataUnit
                                        val value = seriesAndClosestPoint.closestPoint.y
                                        overlayDataEntryLayout(dataName, dataNameShort, dataUnit, value!!)
                                    }
                                }
                        }
                    }
            }
        }
    }

    // Vertical marker line
    linePositionX?.let {
        Spacer(
            Modifier
                .offset(
                    with(LocalDensity.current) { it.toDp() }, 0.dp
                )
                .width(1.dp)
                .fillMaxHeight()
                .background(colors.overlayLine)
        )
    }
}

@Composable
private fun LineChartOverlayInformationWithInterpolatedValues(
    lineChartData: List<LineChartData>,
    xAxisScale: XAxisScale,
    positionX: Float,
    containerSize: Size,
    colors: LineChartColors,
    overlayHeaderLayout: @Composable (value: Any, dataUnit: String?) -> Unit,
    overlayDataEntryLayout: @Composable (dataName: String, dataNameShort: String?, dataUnit: String?, value: Any) -> Unit,
    touchOffsetHorizontal: Dp,
    touchOffsetVertical: Dp,
    overlayWidth: Dp?,
    overlayAlpha: Float,
) {
    if (positionX < 0) {
        return
    }

    val combinedLineChartData by remember(lineChartData) {
        derivedStateOf {
            LineChartData(
                series = lineChartData.flatMap { it.series },
                dataUnit = null,
            )
        }
    }

    val timestampCursor = getTimestampFromCursor(
        xCursorPosition = positionX,
        xAxisScale = xAxisScale,
        containerSize = containerSize
    )
    val listOfValues = retrieveDataWithInterpolatedValue(combinedLineChartData, timestampCursor)

    if (listOfValues.isNotEmpty()) {
        OverlayInformation(
            positionX = positionX,
            containerSize = containerSize,
            backgroundColor = colors.overlaySurface,
            touchOffsetHorizontal = touchOffsetHorizontal,
            touchOffsetVertical = touchOffsetVertical,
            requiredOverlayWidth = overlayWidth,
            overlayAlpha = overlayAlpha,
            content = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    val dataUnit = listOfValues.first().lineChartSeries.dataName // todo
                    val dataNameShort = listOfValues.first().lineChartSeries.dataNameShort
                    overlayHeaderLayout(timestampCursor, dataUnit)

                    Spacer(modifier = Modifier.height(4.dp))

                    listOfValues.forEach { seriesAndInterpolatedValue ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .align(Alignment.Start)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp, 4.dp)
                                    .drawBehind {
                                        drawLine(
                                            strokeWidth = seriesAndInterpolatedValue.lineChartSeries.lineWidth.toPx(),
                                            pathEffect = seriesAndInterpolatedValue.lineChartSeries.pathEffect,
                                            color = seriesAndInterpolatedValue.lineChartSeries.lineColor,
                                            start = Offset(0f, size.height / 2),
                                            end = Offset(size.width, size.height / 2)
                                        )
                                    }
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            val dataName = seriesAndInterpolatedValue.lineChartSeries.dataName
                            val interpolatedValue = seriesAndInterpolatedValue.interpolatedValue
                            overlayDataEntryLayout(dataName, dataNameShort, dataUnit, interpolatedValue)
                        }
                    }
                }
            },
        )
    }

    // Vertical marker line
    Spacer(
        Modifier
            .offset(
                with(LocalDensity.current) { positionX.toDp() }, 0.dp
            )
            .width(1.dp)
            .fillMaxHeight()
            .background(colors.overlayLine)
    )
}

private fun getTimestampFromCursor(
    xCursorPosition: Float,
    containerSize: Size,
    xAxisScale: XAxisScale,
) =
    xCursorPosition.toLong().mapValueToDifferentRange(
        0L,
        containerSize.width.toLong(),
        xAxisScale.start,
        xAxisScale.end,
    )

private fun retrieveDataWithClosestPointForEachSeries(
    lineChartDataList: List<LineChartData>,
    timestampCursor: Long,
): List<SeriesAndClosestPoint> {
    // find time value from position of the cursor

    val outputList: MutableList<SeriesAndClosestPoint> = mutableListOf()
    lineChartDataList.forEach { lineChartData ->
        lineChartData.series.forEach { series ->
            // find the closest point
            series.listOfPoints
                .filter { it.y != null }
                .minByOrNull { abs(it.x - timestampCursor) }
                ?.let {
                    outputList.add(
                        SeriesAndClosestPoint(series, it, lineChartData.dataUnit)
                    )
                }
        }
    }
    return outputList.sortedBy { abs(it.closestPoint.x - timestampCursor) }
}

private fun retrieveDataWithInterpolatedValue(
    lineChartData: LineChartData,
    timestampCursor: Long,
): List<SeriesAndInterpolatedValue> {
    // find time value from position of the cursor

    val outputList: MutableList<SeriesAndInterpolatedValue> = mutableListOf()
    lineChartData.series.forEach { series ->
        // find the point with greater and smaller timestamp
        val v0 = series.listOfPoints
            .filter { it.x >= timestampCursor }
            .minByOrNull { it.x }
        val v1 = series.listOfPoints
            .filter { it.x <= timestampCursor }
            .maxByOrNull { it.x }

        if (v0?.y != null && v1?.y != null) {
            val interpolatedValue =
                interpolateBetweenValues(
                    v0.y,
                    v1.y,
                    // (time - v0.timestamp) to bring down the value and avoid Float overflow
                    (timestampCursor - v0.x).toFloat()
                        // t value has to be between 0 and 1
                        .mapValueToDifferentRange(
                            0f,
                            (v1.x - v0.x).toFloat(), // to avoid Float overflow
                            0f,
                            1f
                        )
                )
            outputList.add(SeriesAndInterpolatedValue(series, interpolatedValue))
        }
    }
    return outputList
}

// simple linear interpolation, should be enough for our purpose
// https://en.wikipedia.org/wiki/Linear_interpolation
private fun interpolateBetweenValues(v0: Float, v1: Float, t: Float): Float {
    return v0 + t * (v1 - v0)
}

@Immutable
private data class SeriesAndInterpolatedValue(
    val lineChartSeries: LineChartSeries,
    val interpolatedValue: Float,
)

@Immutable
data class SeriesAndClosestPoint(
    val lineChartSeries: LineChartSeries,
    val closestPoint: LineChartPoint,
    val dataUnit: String?,
)
