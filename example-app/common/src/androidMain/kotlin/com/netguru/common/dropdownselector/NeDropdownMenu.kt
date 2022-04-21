package com.netguru.common.dropdownselector

import androidx.compose.material.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun <T> NeDropdownMenu(
    expanded: Boolean,
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    items: List<T>,
    onItemClick: (T) -> Unit,
    content: @Composable (T, Boolean) -> Unit
) {
    androidx.compose.material.DropdownMenu(
        expanded = expanded,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        items.forEachIndexed { index, dropdownItem ->
            DropdownMenuItem(
                onClick = { onItemClick(dropdownItem) }
            ) {
                content(dropdownItem, index == items.size - 1)
            }
        }
    }
}
