package com.netguru.charts.gridchart

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object GridDefaults {
    val HORIZONTAL_LINES_OFFSET = 10.dp
    const val NUMBER_OF_GRID_LINES = 5

    val DefaultYAxisMarkerLayout: @Composable (value: Any) -> Unit = { value ->
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }

    val DefaultOverlayHeaderLayout: @Composable (value: Any) -> Unit = { value ->
        Text(
            text = value.toString(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline
        )
    }

    val DefaultOverlayDataEntryLayout: @Composable (dataName: String, value: Any) -> Unit =
        { dataName, value ->
            Text(
                text = "$dataName: $value"
            )
        }

    val DefaultXAxisMarkerLayout: @Composable (value: Any) -> Unit = { value ->
        Text(
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.Center
        )
    }

    val DefaultLegendItemLabel: @Composable (String) -> Unit = {
        Text(
            text = it,
        )
    }
}
