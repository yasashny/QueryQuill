package org.queryquill.app.feature.response.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.feature.response.model.SegmentedButtonState


@Composable
internal fun SegmentedButton(
    currentState: SegmentedButtonState,
    options: ImmutableList<SegmentedButtonState>,
    onClick: (SegmentedButtonState) -> Unit
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