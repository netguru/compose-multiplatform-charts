package com.netguru.multiplatform.charts.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HorizontalDivider(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier
            .height(AppTheme.dimens.borders_thickness)
            .fillMaxWidth(),
        color = AppTheme.colors.borders
    )
}
