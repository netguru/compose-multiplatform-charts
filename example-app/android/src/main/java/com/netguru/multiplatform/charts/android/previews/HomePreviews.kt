package com.netguru.multiplatform.charts.android.previews

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import com.netguru.multiplatform.charts.application.home.HomeDrawerButton
import com.netguru.multiplatform.charts.common.AppTheme
import com.netguru.multiplatform.charts.common.WindowSize

@Composable
@Preview(widthDp = 200)
fun CurrentHomeDrawerButtonPreview() {
    AppTheme(WindowSize.EXPANDED) {
        HomeDrawerButton(
            isCurrent = true,
            onClick = {},
            iconPainter = rememberVectorPainter(Icons.Filled.Person),
            label = "User"
        )
    }
}

@Composable
@Preview(widthDp = 200, showBackground = true)
fun IdleHomeDrawerButtonPreview() {
    AppTheme(WindowSize.EXPANDED) {
        HomeDrawerButton(
            isCurrent = false,
            onClick = {},
            iconPainter = rememberVectorPainter(Icons.Filled.Favorite),
            label = "Favorite"
        )
    }
}
