package com.netguru.multiplatform.charts.grid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.netguru.multiplatform.charts.vertical

internal object GridDefaults {

    val HORIZONTAL_LINES_OFFSET = 0.dp
    const val NUMBER_OF_GRID_LINES = 5
    const val ROUND_MIN_MAX_CLOSEST_TO = 10f

    val YAxisMarkerLayout: @Composable (value: Any) -> Unit = { value ->
        Text(
            modifier = Modifier
                .fillMaxWidth(),
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.End,
            maxLines = 1
        )
    }

    val YAxisDataTitleLayout: @Composable () -> Unit = {
        Text(
            fontSize = 12.sp,
            text = "y-axis label",
            maxLines = 1,
            modifier = Modifier
                .vertical()
        )
    }

    val YAxisDataTitleYAxisData: YAxisTitleData = YAxisTitleData(
        labelLayout = YAxisDataTitleLayout,
        labelPosition = YAxisTitleData.LabelPosition.Left,
    )

    val OverlayHeaderLabel: @Composable (value: Any, dataUnit: String?) -> Unit = { value, dataUnit ->
        Text(
            text = value.toString() + dataUnit?.let { " $it" }.orEmpty(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline
        )
    }

    val OverlayDataEntryLabel: @Composable (dataName: String, dataUnit: String?, value: Any) -> Unit =
        { dataName, dataUnit, value ->
            Text(
                text = "$dataName: $value" + dataUnit?.let { " $it" }.orEmpty()
            )
        }

    val XAxisMarkerLayout: @Composable (value: Any) -> Unit = { value ->
        Text(
            fontSize = 12.sp,
            text = value.toString(),
            textAlign = TextAlign.Center
        )
    }

    val LegendItemLabel: @Composable (name: String, unit: String?) -> Unit = { name, unit ->
        Text(
            text = name + unit?.let { " ($it)" }.orEmpty(),
        )
    }
}
