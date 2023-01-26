package com.netguru.multiplatform.charts.line

sealed class Progression {
    object Linear : Progression()
    class NonLinear(
        val anchorPoints: List<AnchorPoint>,
    ) : Progression() {
        data class AnchorPoint(
            val value: Float,
            val position: Float,
        )
    }
}
