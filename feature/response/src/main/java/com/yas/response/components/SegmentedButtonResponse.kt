package com.yas.response.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.yas.model.ImmutableList
import com.yas.response.ResponseSegmentedButtonState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SegmentedButtonResponse(
    currentState: ResponseSegmentedButtonState,
    options: ImmutableList<ResponseSegmentedButtonState>,
    onClick: (ResponseSegmentedButtonState) -> Unit
) {

    SingleChoiceSegmentedButtonRow {
        options.list.forEachIndexed { index, chipState ->
            SegmentedButton(
                onClick = { onClick(chipState) },
                label = { Text(chipState.title) },
                selected = currentState == chipState,
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.list.size)
            )
        }
    }
}