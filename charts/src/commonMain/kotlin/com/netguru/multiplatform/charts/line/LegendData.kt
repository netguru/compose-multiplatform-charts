package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.grid.GridDefaults

data class LegendData(
    val columnMinWidth: Dp = 200.dp,
    val legendItemLabel: @Composable (name: String, unit: String?) -> Unit = GridDefaults.LegendItemLabel,
)
