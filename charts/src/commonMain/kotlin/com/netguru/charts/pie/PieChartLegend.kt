package com.netguru.charts.pie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.netguru.charts.mapValueToDifferentRange
import com.netguru.charts.theme.ChartColors
import com.netguru.charts.theme.ChartDefaults
import kotlin.math.roundToInt
import kotlin.random.Random

private const val DEFAULT_ANIMATION_DURATION_MS = 300
private const val DEFAULT_ANIMATION_DELAY_MS = 100
private const val PERCENT_SIGN = "%"
private const val ALPHA_MAX = 1f
private const val ALPHA_MIN = 0f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChartLegend(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    columns: Int = 4,
    unit: String = PERCENT_SIGN,
    animate: Boolean = true,
    animationDuration: Int = DEFAULT_ANIMATION_DURATION_MS,
    animationDelay: Int = DEFAULT_ANIMATION_DELAY_MS,
    chartColors: ChartColors = ChartDefaults.chartColors(),
    typography: Typography = MaterialTheme.typography,
) {
    val sum = data.sumOf { it.value }
    LazyVerticalGrid(
        cells = GridCells.Fixed(columns),

        content = {
            items(data.count()) { index ->
                LegendItem(
                    pieChartData = data[index],
                    sumOfDataValues = sum,
                    unit = unit,
                    index = index,
                    animate = animate,
                    animationDuration = animationDuration,
                    animationDelay = animationDelay,
                    chartColors = chartColors,
                    typography = typography,
                )
            }
        }, modifier = modifier
    )
}

@Composable
private fun LegendItem(
    pieChartData: PieChartData,
    sumOfDataValues: Double,
    unit: String,
    index: Int,
    animate: Boolean,
    animationDuration: Int,
    animationDelay: Int,
    chartColors: ChartColors,
    typography: Typography,
) {
    var animationPlayed by remember(animate) {
        mutableStateOf(!animate)
    }

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }

    val alpha by animateFloatAsState(
        targetValue = if (animationPlayed) ALPHA_MAX else ALPHA_MIN,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = index * animationDelay
        )
    )

    val percentage by remember(unit, sumOfDataValues, pieChartData) {
        mutableStateOf(rescaleValueWhenPercent(unit, sumOfDataValues, pieChartData))
    }

    Column(
        modifier = Modifier.alpha(alpha)
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(pieChartData.color)
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = buildAnnotatedString {
                withStyle(
                    style = typography.h5.toSpanStyle()
                        .copy(color = chartColors.primaryText)
                ) {
                    append("${percentage.roundToInt()}")
                }
                withStyle(
                    style = typography.overline.toSpanStyle()
                        .copy(color = chartColors.surface)
                ) {
                    append(" $unit")
                }
            }
        )

        Text(
            text = pieChartData.name,
            style = typography.body2,
            color = chartColors.surface,
        )
    }
}

private fun rescaleValueWhenPercent(unit: String, sum: Double, pieChartData: PieChartData) =
    if (unit == PERCENT_SIGN) {
        pieChartData.value.mapValueToDifferentRange(0.0, sum, 0.0, 100.0)
    } else {
        pieChartData.value
    }

@Composable
private fun LegendPreview() {
    val data = listOf(
        PieChartData("Solar", Random.nextDouble(0.0, 100.0), Color.Yellow),
        PieChartData("Grid", Random.nextDouble(0.0, 100.0), Color.Red),
        PieChartData("Fossil", Random.nextDouble(0.0, 100.0), Color.Black),
        PieChartData("Battery Storage", Random.nextDouble(0.0, 100.0), Color.Blue),
        PieChartData("Other", Random.nextDouble(0.0, 100.0), Color.Gray)
    )
    PieChartLegend(data = data, animate = false, modifier = Modifier.background(Color.White))
}
