package com.netguru.multiplatform.charts.common

import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun ButtonDefaults.noElevation(): ButtonElevation = elevation(
    defaultElevation = 0.dp,
    pressedElevation = 0.dp,
    disabledElevation = 0.dp,
    hoveredElevation = 0.dp,
    focusedElevation = 0.dp
)
