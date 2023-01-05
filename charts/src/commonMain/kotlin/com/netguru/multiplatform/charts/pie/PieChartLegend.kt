package com.netguru.multiplatform.charts.pie

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import com.netguru.multiplatform.charts.ChartDisplayAnimation
import com.netguru.multiplatform.charts.getAnimationAlphas
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PieChartLegend(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartDisplayAnimation = ChartDisplayAnimation.Simple(),
    config: PieChartConfig = PieChartConfig(),
    legendItemLabel: @Composable (PieChartData) -> Unit = PieDefaults.LegendItemLabel,
) {
    val animatedAlpha = getAnimationAlphas(
        animation = animation,
        numberOfElementsToAnimate = data.size,
        uniqueDatasetKey = data,
    )

    val columnsPerRow = when (config.legendOrientation) {
        LegendOrientation.HORIZONTAL -> config.numberOfColsInLegend
        LegendOrientation.VERTICAL -> 1
    }
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.SpaceAround,
        columns = GridCells.Fixed(columnsPerRow),
        content = {
            items(data.count()) { index ->
                LegendItem(
                    pieChartData = data[index],
                    alpha = animatedAlpha[index],
                    config = config,
                    legendItemLabel = legendItemLabel,
                )
            }
        },
        modifier = modifier,
    )
}

@Composable
private fun LegendItem(
    pieChartData: PieChartData,
    alpha: Float,
    config: PieChartConfig,
    legendItemLabel: @Composable (PieChartData) -> Unit,
) {
    when (config.legendOrientation) {
        LegendOrientation.HORIZONTAL ->
            Column(
                modifier = Modifier.alpha(alpha),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier = Modifier
                        .size(config.legendIconSize)
                        .drawBehind {
                            drawLegendIcon(
                                color = pieChartData.color,
                                config = config,
                            )
                        }
                )
                legendItemLabel(pieChartData)
            }

        LegendOrientation.VERTICAL ->
            Row(
                modifier = Modifier.alpha(alpha),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(config.legendIconSize)
                        .drawBehind {
                            drawLegendIcon(
                                color = pieChartData.color,
                                config = config,
                            )
                        }
                )
                legendItemLabel(pieChartData)
            }
    }
}

private fun DrawScope.drawLegendIcon(
    color: Color,
    config: PieChartConfig,
) {
    clipRect {
        when (config.legendIcon) {
            LegendIcon.SQUARE -> drawRect(
                color = color,
            )

            LegendIcon.CIRCLE -> drawCircle(
                color = color
            )

            LegendIcon.ROUND -> drawRoundRect(
                color = color,
                cornerRadius = CornerRadius(
                    config.legendIconSize.toPx() / 4f
                )
            )

            LegendIcon.CAKE -> drawCircle(
                color = color,
                center = Offset(x = 0f, y = size.height),
                radius = size.height
            )
        }
    }
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
    PieChartLegend(data = data, modifier = Modifier.background(Color.White))
}
