package com.netguru.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.netguru.common.AppTheme.colors

@Immutable
data class DotItem(val modifier: Modifier, val color: Color, val radius: Float)

@Composable
fun Dot(dotItem: DotItem) = with(dotItem) {
    with(LocalDensity.current) {
        Canvas(
            modifier = modifier.size((dotItem.radius * 2).dp)
        ) {
            drawCircle(color, radius * density)
        }
    }
}

@Composable
fun ThreeVerticalDots(layoutModifier: Modifier = Modifier, onClick: () -> Unit = {}) =
    with(LocalDensity.current) {
        val dots = listOf(
            DotItem(Modifier, colors.borders, 2f),
            DotItem(Modifier.padding(top = 3.dp, bottom = 3.dp), colors.borders, 2f),
            DotItem(Modifier, colors.borders, 2f)
        )
        Dots(layoutModifier = layoutModifier, verticalDraw = true, dots = dots, onClick)
    }

@Composable
fun Dots(
    layoutModifier: Modifier,
    verticalDraw: Boolean,
    dots: List<DotItem>,
    onClick: () -> Unit
) {
    val modifier = layoutModifier.then(
        Modifier.clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = rememberRipple(bounded = false, radius = 15.dp),
            onClick = { onClick.invoke() }
        )
    )

    if (verticalDraw) {
        Column(modifier) {
            dots.forEach {
                Dot(dotItem = it)
            }
        }
    } else {
        Row(modifier) {
            dots.forEach {
                Dot(dotItem = it)
            }
        }
    }
}
