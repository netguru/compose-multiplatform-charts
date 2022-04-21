package com.netguru.common.dropdownselector

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun SimpleDropdownMenu(
    modifier: Modifier,
    items: List<String>,
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    onItemSelected: (Int) -> Unit
) {
    DropdownMenu(
        modifier = modifier,
        expanded = expanded,
        onDismissRequest = onDismissRequest
    ) {
        items.forEachIndexed() { index, item ->
            DropdownMenuItem(
                onClick = { onItemSelected(index) }
            ) {
                Text(text = item)
            }
        }
    }
}
