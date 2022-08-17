package com.netguru.multiplatform.charts.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.netguru.multiplatform.charts.common.AppTheme

@Composable
fun TitleText(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        color = AppTheme.colors.primaryText,
        fontSize = 40.sp,
        fontWeight = FontWeight.SemiBold,
        modifier = modifier,
    )
}

@Composable
fun SpacedColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimens.grid_4),
        content = content,
    )
}

@Composable
fun ScrollableScreen(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(AppTheme.dimens.grid_4),
        content = content,
    )
}
