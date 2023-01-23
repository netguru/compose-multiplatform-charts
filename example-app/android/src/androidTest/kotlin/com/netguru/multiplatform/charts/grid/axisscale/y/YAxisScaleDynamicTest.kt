package com.netguru.multiplatform.charts.grid.axisscale.y

import org.junit.Test


class YAxisScaleDynamicTest {

//    ///////////////////////////////////////////
//    // min value rounding, non-negative numbers
//    ///////////////////////////////////////////
//    @Test
//    fun minValueOf10_roundedTo10_shouldBe10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 10f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 10f) { "scale.min should be 10.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf10_roundedTo2_shouldBe10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 2f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 10f) { "scale.min should be 10.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf10_roundedTo9_shouldBe9() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 9f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 9f) { "scale.min should be 9.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf10_roundedTo3_shouldBe9() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 3f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 9f) { "scale.min should be 9.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf10_roundedTo20_shouldBe0() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 20f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 0f) { "scale.min should be 0.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf1Point7_roundedTo0Point5_shouldBe1Point5() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 1.7f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 0.5f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == 1.5f) { "scale.min should be 1.5, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOf0_roundedToAnythingPositive_shouldBe0() {
//        (1..250).forEach {
//            val roundTo = it / 2f
//            val scale = YAxisScaleDynamic(
//                minDataValue = 0f,
//                maxDataValue = 20f,
//                maxNumberOfHorizontalLines = 1,
//                roundMinToMultiplicationOf = roundTo,
//                forceShowingValueZeroLine = false,
//            )
//
//            assert(scale.min == 0f) { "scale.min rounded to $roundTo should be 0.0, was: ${scale.min}" }
//        }
//    }
//
//    @Test
//    fun minValueOfAnything_roundToNull_shouldNotBeRounded() {
//        (-125..125).forEach {
//            val minValue = it / 2f
//            val scale = YAxisScaleDynamic(
//                minDataValue = minValue,
//                maxDataValue = 200f,
//                maxNumberOfHorizontalLines = 1,
//                roundMinToMultiplicationOf = null,
//                roundMarkersToMultiplicationOf = null,
//                forceShowingValueZeroLine = false,
//            )
//
//            assert(scale.min == minValue) { "scale.min rounded to null should be $minValue (not rounded at all), was: ${scale.min}" }
//        }
//    }
//
//    ///////////////////////////////////////////
//    // min value rounding, negative numbers
//    ///////////////////////////////////////////
//    @Test
//    fun minValueOfMinus10_roundedTo10_shouldBeMinus10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 10f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -10f) { "scale.min should be -10.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOfMinus10_roundedTo2_shouldBeMinus10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 2f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -10f) { "scale.min should be -10.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOfMinus10_roundedTo9_shouldBeMinus18() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 9f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -18f) { "scale.min should be -18.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOfMinus10_roundedTo3_shouldBeMinus12() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 3f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -12f) { "scale.min should be -12.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOfMinus10_roundedTo20_shouldBeMinus20() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 20f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -20f) { "scale.min should be -20.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun minValueOfMinus1Point7_roundedTo0Point5_shouldBeMinus2() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -1.7f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 0.5f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.min == -2f) { "scale.min should be -2.0, was: ${scale.min}" }
//    }
//
//    ///////////////////////////////////////////
//    // rounding to non-positive
//    ///////////////////////////////////////////
//    @Test(expected = IllegalArgumentException::class)
//    fun valuesOfAnything_roundingMinToNegative_shouldThrowExceptionWhenCreatingTheInstance() {
//        YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = -3f,
//            roundMarkersToMultiplicationOf = 1f,
//            forceShowingValueZeroLine = false,
//        )
//    }
//
//    @Test(expected = IllegalArgumentException::class)
//    fun valuesOfAnything_roundingMinToZero_shouldThrowExceptionWhenCreatingTheInstance() {
//        YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 0f,
//            roundMarkersToMultiplicationOf = 1f,
//            forceShowingValueZeroLine = false,
//        )
//    }
//
//    @Test(expected = IllegalArgumentException::class)
//    fun valuesOfAnything_roundingMaxToNegative_shouldThrowExceptionWhenCreatingTheInstance() {
//        YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 1f,
//            roundMarkersToMultiplicationOf = -3f,
//            forceShowingValueZeroLine = false,
//        )
//    }
//
//    @Test(expected = IllegalArgumentException::class)
//    fun valuesOfAnything_roundingMaxToZero_shouldThrowExceptionWhenCreatingTheInstance() {
//        YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 0f,
//            forceShowingValueZeroLine = false,
//        )
//    }
//
//
//    ///////////////////////////////////////////
//    // max value rounding, non-negative numbers
//    ///////////////////////////////////////////
//    @Test
//    fun maxValueOf10_roundedTo10_shouldBe10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMinToMultiplicationOf = 10f,
//            roundMarkersToMultiplicationOf = 10f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 10f) { "scale.max should be 10.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf10_roundedTo2_shouldBe10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 2f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 10f) { "scale.max should be 10.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf10_roundedTo9_shouldBe18() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 9f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 18f) { "scale.max should be 18.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf10_roundedTo3_shouldBe12() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 3f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 12f) { "scale.max should be 12.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf10_roundedTo20_shouldBe20() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 20f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 20f) { "scale.max should be 20.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf1Point7_roundedTo0Point5_shouldBe2() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 0f,
//            maxDataValue = 1.7f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 0.5f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 2f) { "scale.max should be 2.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOf0_roundedToAnythingPositive_shouldBe0() {
//        (1..250).forEach {
//            val roundTo = it / 2f
//            val scale = YAxisScaleDynamic(
//                minDataValue = -10f,
//                maxDataValue = 0f,
//                maxNumberOfHorizontalLines = 1,
//                roundMaxToMultiplicationOf = roundTo,
//                forceShowingValueZeroLine = false,
//            )
//
//            assert(scale.max == 0f) { "scale.max rounded to $roundTo should be 0.0, was: ${scale.max}" }
//        }
//    }
//
//    @Test
//    fun maxValueOfAnything_roundToNull_shouldNotBeRounded() {
//        (-125..125).forEach {
//            val maxValue = it / 2f
//            val scale = YAxisScaleDynamic(
//                minDataValue = -200f,
//                maxDataValue = maxValue,
//                maxNumberOfHorizontalLines = 1,
//                roundMinToMultiplicationOf = null,
//                roundMarkersToMultiplicationOf = null,
//                forceShowingValueZeroLine = false,
//            )
//
//            assert(scale.max == maxValue) { "scale.max rounded to null should be $maxValue (not rounded at all), was: ${scale.max}" }
//        }
//    }
//
//    ///////////////////////////////////////////
//    // max value rounding, negative numbers
//    ///////////////////////////////////////////
//    @Test
//    fun maxValueOfMinus10_roundedTo10_shouldBeMinus10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 10f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == -10f) { "scale.max should be -10.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOfMinus10_roundedTo2_shouldBeMinus10() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 2f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == -10f) { "scale.max should be -10.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOfMinus10_roundedTo9_shouldBeMinus9() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 9f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == -9f) { "scale.max should be -9.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOfMinus10_roundedTo3_shouldBeMinus9() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 3f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == -9f) { "scale.max should be -9.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOfMinus10_roundedTo20_shouldBe0() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -10f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 20f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == 0f) { "scale.max should be 0.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun maxValueOfMinus1Point7_roundedTo0Point5_shouldBeMinus1Point5() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -17f,
//            maxDataValue = -1.7f,
//            maxNumberOfHorizontalLines = 1,
//            roundMaxToMultiplicationOf = 0.5f,
//            forceShowingValueZeroLine = false,
//        )
//
//        assert(scale.max == -1.5f) { "scale.max should be -1.5, was: ${scale.max}" }
//    }
//
//
//    ///////////////////////////////////////////
//    // force showing value zero line
//    ///////////////////////////////////////////
//    @Test
//    fun minValueGT0_notRoundedAndForceShowingValueZeroLine_minIsZero() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = 10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            forceShowingValueZeroLine = true,
//        )
//
//        assert(scale.min == 0f) { "scale.min should be forced to 0.0, was: ${scale.min}" }
//    }
//
//    @Test
//    fun maxValueLT0_notRoundedAndForceShowingValueZeroLine_maxIsZero() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -100f,
//            maxDataValue = -20f,
//            maxNumberOfHorizontalLines = 1,
//            forceShowingValueZeroLine = true,
//        )
//
//        assert(scale.max == 0f) { "scale.max should be forced to 0.0, was: ${scale.max}" }
//    }
//
//    @Test
//    fun minValueLT0AndMaxValueGT0_notRoundedAndForceShowingValueZeroLine_maxIsMaxMinIsMin() {
//        val scale = YAxisScaleDynamic(
//            minDataValue = -10f,
//            maxDataValue = 20f,
//            maxNumberOfHorizontalLines = 1,
//            forceShowingValueZeroLine = true,
//        )
//
//        assert(scale.min == -10f) { "scale.min should be -10.0, was: ${scale.min}" }
//        assert(scale.max == 20f) { "scale.max should be 20.0, was: ${scale.max}" }
//    }
}
