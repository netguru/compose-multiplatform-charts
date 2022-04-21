package com.netguru.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun imageResources(image: String): Painter {
    val context = LocalContext.current
    val name = image.substringBefore(".")
    val drawable = context.resources.getIdentifier(name, "drawable", context.packageName)
    return painterResource(drawable)
}

@Composable
actual fun fontResources(
    font: String,
    weight: FontWeight,
    style: FontStyle
): Font {
    val context = LocalContext.current
    val name = font.substringBefore(".")
    val fontRes = context.resources.getIdentifier(name, "font", context.packageName)
    return Font(fontRes, weight, style)
}
