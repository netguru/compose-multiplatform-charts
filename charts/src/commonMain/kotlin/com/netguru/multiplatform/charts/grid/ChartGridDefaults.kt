package com.netguru.multiplatform.charts.grid

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.netguru.multiplatform.charts.grid.axisscale.y.YAxisScaleDynamic
import com.netguru.multiplatform.charts.line.YAxisConfig
import com.netguru.multiplatform.charts.vertical

internal object ChartGridDefaults {

    const val NUMBER_OF_GRID_LINES = 5
    const val ROUND_Y_AXIS_MARKERS_CLOSEST_TO = 10f
    const val ROUND_X_AXIS_MARKERS_CLOSEST_TO = 15 * 60 * 1000L // 15 minutes

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

    val YAxisDataTitle: YAxisTitleData = YAxisTitleData(
        labelLayout = YAxisDataTitleLayout,
        labelPosition = YAxisTitleData.LabelPosition.Left,
    )

    val TooltipHeaderLabel: @Composable (value: Any, dataUnit: String?) -> Unit = { value, dataUnit ->
        Text(
            text = value.toString() + dataUnit?.let { " $it" }.orEmpty(),
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.overline
        )
    }

    val TooltipDataEntryLabel: @Composable (dataName: String, dataNameShort: String?, dataUnit: String?, value: Any) -> Unit =
        { dataName, dataNameShort, dataUnit, value ->
            Text(
                text = "$dataName${dataNameShort?.let { " ($it)" }.orEmpty()}: $value" + dataUnit?.let { " $it" }
                    .orEmpty()
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

    @Composable
    fun yAxisConfig(data: GridChartData) = remember(data) {
        YAxisConfig(
            scale = YAxisScaleDynamic(
                chartData = data,
            )
        )
    }
}
