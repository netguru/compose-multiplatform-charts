package com.netguru.multiplatform.charts

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.tween

sealed class ChartDisplayAnimation {
    object Disabled : ChartDisplayAnimation()

    class Simple(
        val animationSpec: () -> AnimationSpec<Float> = {
            tween(DEFAULT_DURATION, DEFAULT_DELAY)
        },
    ) : ChartDisplayAnimation()

    class Sequenced(
        val animationSpec: (dataSeriesIndex: Int) -> AnimationSpec<Float> = { index ->
            tween(DEFAULT_DURATION, index * DEFAULT_DELAY)
        },
    ) : ChartDisplayAnimation()

    private companion object {
        const val DEFAULT_DURATION = 300
        const val DEFAULT_DELAY = 100
    }
}
