package com.netguru.charts.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal val LocalDimens = staticCompositionLocalOf {
    ChartsDimens.smallDimens
}

class ChartsDimens(
    val grid_0_5: Dp,
    val grid_1: Dp,
    val grid_2: Dp,
    val grid_3: Dp,
) {
    companion object {
        val smallDimens = ChartsDimens(
            grid_0_5 = 3.dp,
            grid_1 = 6.dp,
            grid_2 = 12.dp,
            grid_3 = 18.dp,
        )

        val sw600Dimens = ChartsDimens(
            grid_0_5 = 4.dp,
            grid_1 = 8.dp,
            grid_2 = 16.dp,
            grid_3 = 24.dp,
        )

        val sw840Dimens = ChartsDimens(
            grid_0_5 = 5.dp,
            grid_1 = 10.dp,
            grid_2 = 20.dp,
            grid_3 = 30.dp,
        )
    }
}
