package com.yas.queryquill.components

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
import com.yas.model.BasicState

@Composable
fun ChipGroup(
    currentState: BasicState, options: List<BasicState>, onClick: (BasicState) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(start = 29.dp, end = 15.dp)
        ) {
            options.forEach { chipState ->
                InputChip(modifier = Modifier.padding(horizontal = 4.dp),
                    onClick = { onClick(chipState) },
                    label = { Text(chipState.name) },
                    selected = currentState::class == chipState::class,
                    leadingIcon = {
                        if (currentState::class == chipState::class) {
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