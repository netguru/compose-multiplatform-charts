package com.netguru.charts.theme

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
        primaryText: Color = MaterialTheme.colors.secondary,
        surface: Color = Color.Unspecified,
        borders: Color = Color.Unspecified,
        grid: Color = MaterialTheme.colors.onSurface.copy(alpha = 0.4f),
        labels: Color = Color.Unspecified,
        bubbleText: Color = Color.White,
        emptyGasBottle: Color = MaterialTheme.colors.error,
        fullGasBottle: Color = MaterialTheme.colors.primary,
        overlayLine: Color = MaterialTheme.colors.error,
    ) = ChartColors(
        primary = primary,
        primaryText = primaryText,
        surface = surface,
        borders = borders,
        grid = grid,
        labels = labels,
        bubbleText = bubbleText,
        emptyGasBottle = emptyGasBottle,
        fullGasBottle = fullGasBottle,
        overlayLine = overlayLine,
    )
}
