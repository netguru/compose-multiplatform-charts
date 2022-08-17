package com.netguru.multiplatform.charts.dial

import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object DialDefaults {

    val THICKNESS = 15.dp
    val SCALE_PADDING = 24.dp
    val SCALE_STROKE_WIDTH = 2.dp
    val SCALE_STROKE_LENGTH = 16.dp
    const val START_ANGLE = -180f
    val JOIN_STYLE = DialJoinStyle.WithDegreeGap(2f)

    val MainLabel: @Composable (value: Any) -> Unit = {
        Text(text = it.toString(), fontSize = 24.sp)
    }

    val MinAndMaxValueLabel: @Composable (value: Any) -> Unit = {
        Text(
            text = it.toString(),
            style = MaterialTheme.typography.body2,
            modifier = Modifier
                .padding(top = 16.dp)
        )
    }
}
