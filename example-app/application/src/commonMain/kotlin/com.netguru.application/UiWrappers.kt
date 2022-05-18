package com.netguru.application

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.netguru.common.AppTheme

@Composable
fun TitleText(
    text: String,
) {
    Text(
        text = text,
        color = AppTheme.colors.primaryText,
    )
}

@Composable
fun SpacedColumn(
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(30.dp),
        content = content,
    )
}
