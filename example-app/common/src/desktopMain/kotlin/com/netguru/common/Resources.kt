package com.netguru.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
actual fun imageResources(image: String): Painter = painterResource("drawable/$image")

actual fun fontResources(
    font: String,
    weight: FontWeight,
    style: FontStyle
): Font = androidx.compose.ui.text.platform.Font("font/$font", weight, style)
