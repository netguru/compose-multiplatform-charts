package com.netguru.charts.bubblechart

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.netguru.charts.round

internal object BubbleDefaults {

    const val MINIMUM_BUBBLE_RADIUS = 40f

    val BubbleLabel: @Composable (Bubble) -> Unit = { bubble ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(bubble.radius.dp * 1.6f)
        ) {
            Icon(
                painter = rememberVectorPainter(bubble.icon),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(20.dp),
            )

            Spacer(Modifier.height(4.dp))

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bubble.value.round(1),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = bubble.name,
                color = Color.White.copy(alpha = 0.6f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
