package com.netguru.multiplatform.charts.pie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import com.netguru.multiplatform.charts.ChartAnimation
import kotlin.random.Random

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun PieChartLegend(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    animation: ChartAnimation = ChartAnimation.Simple(),
    config: PieChartConfig = PieChartConfig(),
    legendItemLabel: @Composable (PieChartData) -> Unit = PieDefaults.LegendItemLabel,
) {
    var animationPlayed by remember(data) {
        mutableStateOf(animation is ChartAnimation.Disabled)
    }
    LaunchedEffect(Unit) {
        animationPlayed = true
    }
    val animatedAlpha = when (animation) {
        ChartAnimation.Disabled -> data.indices.map { 1f }
        is ChartAnimation.Simple -> data.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec()
            ).value
        }
        is ChartAnimation.Sequenced -> data.indices.map {
            animateFloatAsState(
                targetValue = if (animationPlayed) 1f else 0f,
                animationSpec = animation.animationSpec(it)
            ).value
        }
    }
    val columnsPerRow = when (config.legendOrientation) {
        LegendOrientation.HORIZONTAL -> config.numberOfColsInLegend
        LegendOrientation.VERTICAL -> 1
    }
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceAround,
        verticalArrangement = Arrangement.SpaceAround,
        cells = GridCells.Fixed(columnsPerRow),
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
