package com.netguru.charts.gridchart

import kotlin.test.Test
import kotlin.test.assertEquals

class ChartAxisKtTest {

    data class TestData(
        val inputNumber: Number,
        val decimalPlaces: Int,
        val expected: String,
    )

    private val testDataList = listOf(
        TestData(0, 0, "0"),
        TestData(0L, 0, "0"),
        TestData(0f, 0, "0"),
        TestData(0.0, 0, "0"),

        TestData(-0, 0, "0"),
        TestData(-0L, 0, "0"),
        TestData(-0f, 0, "0"),
        TestData(-0.0, 0, "0"),

        TestData(0, 2, "0"),
        TestData(0L, 2, "0"),
        TestData(0f, 2, "0"),
        TestData(0.0, 2, "0"),

        TestData(1, 0, "1"),
        TestData(1L, 0, "1"),
        TestData(1f, 0, "1"),
        TestData(1.0, 0, "1"),

        TestData(-1, 0, "-1"),
        TestData(-1L, 0, "-1"),
        TestData(-1f, 0, "-1"),
        TestData(-1.0, 0, "-1"),

        TestData(1, 1, "1.0"),
        TestData(1L, 1, "1.0"),
        TestData(1f, 1, "1.0"),
        TestData(1.0, 1, "1.0"),

        TestData(-1, 1, "-1.0"),
        TestData(-1L, 1, "-1.0"),
        TestData(-1f, 1, "-1.0"),
        TestData(-1.0, 1, "-1.0"),

        TestData(1, 1, "1.0"),
        TestData(1L, 1, "1.0"),
        TestData(1.1f, 1, "1.1"),
        TestData(1.1, 1, "1.1"),

        TestData(1, 2, "1.00"),
        TestData(1L, 2, "1.00"),
        TestData(1.234f, 2, "1.23"),
        TestData(1.234, 2, "1.23"),

        TestData(-1, 2, "-1.00"),
        TestData(-1L, 2, "-1.00"),
        TestData(-1.234f, 2, "-1.23"),
        TestData(-1.234, 2, "-1.23"),

        TestData(1, 2, "1.00"),
        TestData(1L, 2, "1.00"),
        TestData(1.239f, 2, "1.24"),
        TestData(1.239, 2, "1.24"),

        TestData(-1, 2, "-1.00"),
        TestData(-1L, 2, "-1.00"),
        TestData(-1.239f, 2, "-1.24"),
        TestData(-1.239, 2, "-1.24"),
    )

    @Test
    fun testFormatForYLabel() {
        testDataList.forEach {
            assertEquals(
                expected = it.expected,
                actual = it.inputNumber.formatForYLabel(it.decimalPlaces),
                message = "Test failed for data: $it"
            )
        }
    }
}
