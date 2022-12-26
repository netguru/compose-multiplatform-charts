package com.netguru.multiplatform.charts.line

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.netguru.multiplatform.charts.grid.GridDefaults

data class OverlayData(
    val overlayHeaderLabel: @Composable (value: Any, dataUnit: String?) -> Unit = GridDefaults.OverlayHeaderLabel,
    val overlayDataEntryLabel: @Composable (dataName: String, dataNameShort: String?, dataUnit: String?, value: Any) -> Unit = GridDefaults.OverlayDataEntryLabel,
    val showEnlargedPointOnLine: Boolean = false,
    val showInterpolatedValues: Boolean = true,
    val highlightPointsCloserThan: Dp = 30.dp,
    val touchOffsetHorizontal: Dp = 20.dp,
    val touchOffsetVertical: Dp = 20.dp,
    val overlayWidth: Dp? = 200.dp,
    val overlayAlpha: Float = 0.9f,
)
