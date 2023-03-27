package com.netguru.multiplatform.charts.gasbottle

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.netguru.multiplatform.charts.theme.ChartColors

@Immutable
data class GasBottleColors(
    val fullGasBottle: Color,
    val emptyGasBottle: Color,
)

val ChartColors.gasBottleColors
    get() = GasBottleColors(
        fullGasBottle = fullGasBottle,
        emptyGasBottle = emptyGasBottle,
    )
