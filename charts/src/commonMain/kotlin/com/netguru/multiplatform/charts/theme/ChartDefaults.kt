package com.netguru.multiplatform.charts.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/**
 * Contains the default values used by all charts
 */
object ChartDefaults {
    @Composable
    fun chartColors(
        primary: Color = MaterialTheme.colors.primary,
        surface: Color = Color.Unspecified,
        grid: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
        emptyGasBottle: Color = MaterialTheme.colors.error,
        fullGasBottle: Color = MaterialTheme.colors.primary,
        overlayLine: Color = MaterialTheme.colors.error,
    ) = ChartColors(
        primary = primary,
        surface = surface,
        grid = grid,
        emptyGasBottle = emptyGasBottle,
        fullGasBottle = fullGasBottle,
        overlayLine = overlayLine,
    )
}
