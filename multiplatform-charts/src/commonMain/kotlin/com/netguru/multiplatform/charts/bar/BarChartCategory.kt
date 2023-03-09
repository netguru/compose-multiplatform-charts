package com.netguru.multiplatform.charts.bar

import androidx.compose.runtime.Immutable

@Immutable
data class BarChartCategory(
    val name: String,
    val entries: List<BarChartEntry>
) {
    val minY: Float
        get() = entries.minOf { it.y }
    val maxY: Float
        get() = entries.maxOf { it.y }
}
