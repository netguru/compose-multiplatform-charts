package com.netguru.application.screen

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.GridItemSpan
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.application.SpacedColumn
import com.netguru.application.TitleText
import com.netguru.charts.ChartAnimation
import com.netguru.charts.gasbottle.GasBottle
import com.netguru.common.AppTheme
import com.netguru.common.WindowSize

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GasBottleChartScreen() {

    val gasBottleModifier = Modifier
        .width(200.dp)
        .height(300.dp)

    val gasBottles = arrayListOf(
        GasBottleItem(name = "Cylinder A", value = 0.3f),
        GasBottleItem(name = "Cylinder B", value = 1.6f),
        GasBottleItem(name = "Cylinder C", value = 1.6f),
        GasBottleItem(name = "Cylinder D", value = 0.1f),
    )

    val numberOfCols = when (AppTheme.windowSize) {
        WindowSize.COMPACT -> 1
        WindowSize.MEDIUM -> 2
        WindowSize.EXPANDED -> 4
    }

    SpacedColumn {
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(),
            cells = GridCells.Fixed(numberOfCols),
            contentPadding = PaddingValues(AppTheme.dimens.grid_4),
        ) {
            item(span = { GridItemSpan(numberOfCols) }) {
                TitleText(
                    text = "Gas Bottle",
                    modifier = Modifier
                        .padding(bottom = AppTheme.dimens.grid_3)
                        .fillMaxWidth()
                )
            }
            items(gasBottles.size) { index ->
                val item = gasBottles[index]
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_3),
                    modifier = Modifier.padding(vertical = AppTheme.dimens.grid_3),
                ) {
                    Text(
                        text = item.name,
                        color = AppTheme.colors.primaryText,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                    GasBottle(
                        percentage = 100 * item.value / item.capacity,
                        modifier = gasBottleModifier,
                        animation = ChartAnimation.Simple {
                            spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessVeryLow
                            )
                        }
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_0_5),
                    ) {
                        Row(
                            verticalAlignment = Alignment.Bottom,
                            horizontalArrangement = Arrangement.Center,
                        ) {
                            Text(
                                text = "${item.value}",
                                color = item.valueColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium
                            )
                            Text(" / ${item.capacity} kg", color = AppTheme.colors.secondaryText)
                        }
                        Text("available", color = AppTheme.colors.secondaryText)
                    }
                }
            }
        }
    }
}

data class GasBottleItem(
    val name: String,
    val value: Float,
    val capacity: Float = 1.6f
)

private val GasBottleItem.valueColor: Color
    @Composable
    get() = if (value > capacity * 0.1f) AppTheme.colors.success else AppTheme.colors.danger
