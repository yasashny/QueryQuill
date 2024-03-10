package ru.yasdev.queryquill.components

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
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChipGroupSingleLine(selectedIndex: MutableState<Int>, options: List<String>, onClick: (Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            options.forEachIndexed { index, label ->
                InputChip(
                    modifier = if(index == 0){Modifier.padding(start = 29.dp, end = 4.dp)}else{Modifier.padding(horizontal = 4.dp)},
                    onClick = { onClick(index) },
                    label = { Text(label) },
                    selected = index == selectedIndex.value,
                    leadingIcon = {
                        if (index == selectedIndex.value){
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized Description",
                                modifier = Modifier.size(FilterChipDefaults.IconSize)
                            )
                        }

                    }
                )
            }
        }
    }
}