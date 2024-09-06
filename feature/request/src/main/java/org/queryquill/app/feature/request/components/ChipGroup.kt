package org.queryquill.app.feature.request.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.ImmutableList

@Composable
internal fun <T : Enum<T>> ChipGroup(
    currentState: T, options: ImmutableList<T>, onClick: (T) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 29.dp, end = 15.dp)
        ) {
            options.list.forEach { chipState ->
                InputChip(modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { onClick(chipState) },
                    label = { Text(chipState.name) },
                    selected = currentState == chipState,
                    leadingIcon = {
                        if (currentState == chipState) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = null,
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }
                    })
            }
        }
    }
}