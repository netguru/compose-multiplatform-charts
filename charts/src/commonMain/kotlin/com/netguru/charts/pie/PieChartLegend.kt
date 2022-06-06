package com.netguru.charts.pie

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.netguru.charts.ChartAnimation
import kotlin.random.Random

/**
 * Version of [PieChart] with legend.
 *
 * @param columns Number of columns in the legend
 * @param legendItemLabel Composable to use to represent the item in the legend
 *
 * @see PieChart
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PieChartLegend(
    data: List<PieChartData>,
    modifier: Modifier = Modifier,
    columns: Int = PieDefaults.NUMBER_OF_COLS_IN_LEGEND,
    animation: ChartAnimation = ChartAnimation.Simple(),
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
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.SpaceAround,
        cells = GridCells.Fixed(columns),
        content = {
            items(data.count()) { index ->
                LegendItem(
                    pieChartData = data[index],
                    alpha = animatedAlpha[index],
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
    legendItemLabel: @Composable (PieChartData) -> Unit,
) {
    Column(
        modifier = Modifier.alpha(alpha),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(pieChartData.color)
        )

        legendItemLabel(pieChartData)
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
