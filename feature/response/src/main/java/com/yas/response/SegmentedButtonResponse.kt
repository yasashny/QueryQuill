package com.yas.response

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SegmentedButtonResponse(
    currentState: ResponseSegmentedButtonState,
    options: List<ResponseSegmentedButtonState>,
    onClick: (ResponseSegmentedButtonState) -> Unit
) {

    SingleChoiceSegmentedButtonRow {
        options.forEachIndexed { index, chipState ->
            SegmentedButton(
                onClick = { onClick(chipState) },
                label = { Text(chipState.title) },
                selected = currentState == chipState,
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size)
            )
        }
    }
}