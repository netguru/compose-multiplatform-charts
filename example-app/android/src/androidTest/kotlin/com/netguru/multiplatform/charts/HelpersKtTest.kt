package com.netguru.multiplatform.charts

import org.junit.Test

class HelpersKtTest {

    @Test
    fun roundToMultiplicationOf_initialIsPositive_roundToCeiling() {
        val initial = 20f
        val target = 30f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, true)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test
    fun roundToMultiplicationOf_initialIsPositive_roundToFloor() {
        val initial = 20f
        val target = 15f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, false)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test
    fun roundToMultiplicationOf_initialIsNegative_roundToCeiling() {
        val initial = -20f
        val target = -15f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, true)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test
    fun roundToMultiplicationOf_initialIsNegative_roundToFloor() {
        val initial = -20f
        val target = -30f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, false)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test
    fun roundToMultiplicationOf_initialIsZero_roundToFloor_resultIsZero() {
        val initial = 0f
        val target = 0f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, false)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test
    fun roundToMultiplicationOf_initialIsZero_roundToCeiling_resultIsZero() {
        val initial = 0f
        val target = 0f
        val multiplication = 15f

        val result = initial.roundToMultiplicationOf(multiplication, true)
        assert(result == target) { "result should be $target, but was $result" }
    }

    @Test(expected = IllegalArgumentException::class)
    fun roundToMultiplicationOf_multiplicandIsZero_exceptionIsThrown() {
        1f.roundToMultiplicationOf(0f, true)
    }
}
