package com.yas.queryquill.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.yas.queryquill.screens.requestScreens.httpRequestScreen.requestScreenHeader.HttpRequestHeaderState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SegmentedButtonSingleSelect(
    selectedIndex: MutableState<HttpRequestHeaderState>,
    options: List<HttpRequestHeaderState>,
    onClick: (HttpRequestHeaderState) -> Unit
) {
    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, element ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                onClick = { onClick(element) },
                selected = element == selectedIndex.value
            ) {
                Text(element.title)
            }
        }
    }
}