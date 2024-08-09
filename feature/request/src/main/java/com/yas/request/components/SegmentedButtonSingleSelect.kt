package com.yas.request.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.yas.model.ImmutableList
import com.yas.request.requestScreenHeader.HttpRequestHeaderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SegmentedButtonSingleSelect(
    selectedIndex: MutableState<HttpRequestHeaderState>,
    options: ImmutableList<HttpRequestHeaderState>,
    onClick: (HttpRequestHeaderState) -> Unit
) {
    SingleChoiceSegmentedButtonRow {
        options.list.forEachIndexed { index, element ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.list.size),
                onClick = { onClick(element) },
                selected = element == selectedIndex.value
            ) {
                Text(element.title)
            }
        }
    }
}