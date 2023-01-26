package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.grid.ChartGridDefaults

data class TooltipConfig(
    val headerLabel: @Composable (value: Any, dataUnit: String?) -> Unit = ChartGridDefaults.TooltipHeaderLabel,
    val dataEntryLabel: @Composable (dataName: String, dataNameShort: String?, dataUnit: String?, value: Any) -> Unit = ChartGridDefaults.TooltipDataEntryLabel,
    val showEnlargedPointOnLine: Boolean = false,
    val showInterpolatedValues: Boolean = true,
    val highlightPointsCloserThan: Dp = 30.dp,
    val touchOffsetHorizontal: Dp = 20.dp,
    val touchOffsetVertical: Dp = 20.dp,
    val width: Dp? = 200.dp,
    val alpha: Float = 0.9f,
)
