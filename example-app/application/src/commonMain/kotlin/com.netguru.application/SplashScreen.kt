package com.netguru.application

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.netguru.common.AppTheme
import com.netguru.common.AppTheme.drawables
import com.netguru.common.imageResources

@Composable
fun SplashScreen() {
    Scaffold {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(AppTheme.colors.surface).fillMaxSize()
        ) {
            Image(painter = imageResources(drawables.netguru_logo_dark), contentDescription = null)
        }
    }
}
