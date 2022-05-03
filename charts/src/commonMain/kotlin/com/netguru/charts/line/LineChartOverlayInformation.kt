package com.netguru.charts.line

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.round
import com.netguru.charts.theme.ChartColors

private val TOUCH_OFFSET = 20.dp
private val OVERLAY_WIDTH = 200.dp

@Composable
internal fun OverlayInformation(
    lineChartData: LineChartData,
    positionX: Float,
    containerSize: Size,
    chartColors: ChartColors,
    timeFormatter: (Long) -> String,
    typography: Typography,
) {
    if (positionX < 0) return

    BoxWithConstraints(
        modifier = Modifier
            .offset(
                x = with(LocalDensity.current) {
                    positionX.toDp() +
                            // change offset based on cursor position to avoid out of screen drawing on the right
                            if (positionX.toDp() > (containerSize.width / 2).toDp()) {
                                -OVERLAY_WIDTH - TOUCH_OFFSET
                            } else {
                                TOUCH_OFFSET
                            }
                },
                y = TOUCH_OFFSET
            )
            .width(OVERLAY_WIDTH)
            .alpha(0.9f)
            .clip(RoundedCornerShape(10.dp))
            .background(chartColors.surface)
            .padding(8.dp)
    ) {

        Column {
            val timestampCursor = getTimestampFromCursor(
                xCursorPosition = positionX,
                lineChartData = lineChartData,
                containerSize = containerSize
            )
            val listOfValues = retrieveData(lineChartData, timestampCursor)

            Text(
                text = timeFormatter(timestampCursor),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = typography.overline
            )

            Spacer(modifier = Modifier.height(4.dp))

            listOfValues.forEach { seriesAndInterpolatedValue ->
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(16.dp, 4.dp)
                            .drawBehind {
                                drawLine(
                                    strokeWidth = seriesAndInterpolatedValue.lineChartSeries.lineWidth.toPx(),
                                    pathEffect = if (seriesAndInterpolatedValue.lineChartSeries.dashedLine)
                                        dashedPathEffect else null,
                                    color = seriesAndInterpolatedValue.lineChartSeries.lineColor,
                                    start = Offset(0f, size.height / 2),
                                    end = Offset(size.width, size.height / 2)
                                )
                            }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    val dataName = seriesAndInterpolatedValue.lineChartSeries.dataName
                    val roundedValue = seriesAndInterpolatedValue.interpolatedValue.round()
                    Text(
                        modifier = Modifier,
                        text = "$dataName: $roundedValue ${lineChartData.units}",
                        color = chartColors.labels
                    )
                }
            }
        }
    }

    // Vertical marker line
    Spacer(
        Modifier
            .offset(
                with(LocalDensity.current) { positionX.toDp() }, 0.dp
            )
            .width(1.dp)
            .fillMaxHeight()
            .background(chartColors.overlayLine)
    )
}

private fun getTimestampFromCursor(
    xCursorPosition: Float,
    lineChartData: LineChartData,
    containerSize: Size,
) =
    xCursorPosition.toLong().mapValueToDifferentRange(
        0L,
        containerSize.width.toLong(),
        lineChartData.minX,
        lineChartData.maxX
    )

private fun retrieveData(
    lineChartData: LineChartData,
    timestampCursor: Long,
): List<SeriesAndInterpolatedValue> {
    // find time value from position of the cursor

    val outputList: MutableList<SeriesAndInterpolatedValue> = mutableListOf()
    lineChartData.series.forEach { series ->
        // find the point with greater and smaller timestamp
        val v0 = series.listOfPoints
            .filter { it.timestamp >= timestampCursor }
            .minByOrNull { it.timestamp }
        val v1 = series.listOfPoints
            .filter { it.timestamp <= timestampCursor }
            .maxByOrNull { it.timestamp }

        if (v0 != null && v1 != null) {
            val interpolatedValue =
                interpolateBetweenValues(
                    v0.value,
                    v1.value,
                    // (time - v0.timestamp) to bring down the value and avoid Float overflow
                    (timestampCursor - v0.timestamp).toFloat()
                        // t value has to be between 0 and 1
                        .mapValueToDifferentRange(
                            0f,
                            (v1.timestamp - v0.timestamp).toFloat(), // to avoid Float overflow
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
