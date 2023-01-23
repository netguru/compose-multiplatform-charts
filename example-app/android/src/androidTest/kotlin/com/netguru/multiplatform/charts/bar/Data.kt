package com.netguru.multiplatform.charts.bar

import androidx.compose.ui.graphics.Color
import kotlin.math.roundToInt

internal object Data {
    internal enum class ValueTypes {
        POSITIVE,
        NEGATIVE,
        POSITIVE_AND_NEGATIVE,
        ZERO,
    }

    private val colors = listOf(
        Color.Blue,
        Color.Cyan,
        Color.Black,
        Color.DarkGray,
        Color.Green,
        Color.LightGray,
        Color.Magenta,
        Color.Red,
        Color.Yellow,
    )

    private fun generateEntries(n: Int, valueTypes: ValueTypes): List<BarChartEntry> {
        val nonZeroValues = when (valueTypes) {
            ValueTypes.POSITIVE -> 1..n
            ValueTypes.NEGATIVE -> -n..-1
            ValueTypes.POSITIVE_AND_NEGATIVE -> ((-n / 2)..-1) + (1..(n/2.0).roundToInt())
            ValueTypes.ZERO -> List(n) { 0 }
        }
        return nonZeroValues.map {
            BarChartEntry(
                x = "entry_$it",
                y = it * 10f,
                color = colors.elementAt(it.mod(colors.size))
            )
        }
    }

    private fun generateCategories(
        nOfCategories: Int,
        nOfEntries: Int,
        valueTypes: ValueTypes
    ): List<BarChartCategory> {
        return (1..nOfCategories).map {
            BarChartCategory(
                name = "category_$it",
                entries = generateEntries(nOfEntries, valueTypes),
            )
        }
    }

    fun generateData(nOfCategories: Int, nOfEntries: Int, valueTypes: ValueTypes): BarChartData {
        return BarChartData(
            categories = generateCategories(nOfCategories, nOfEntries, valueTypes),
            unit = "unit",
        )
    }
}
