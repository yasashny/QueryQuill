package org.queryquill.app.feature.request.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import org.queryquill.app.core.model.ImmutableList
import org.queryquill.app.feature.request.ScreenState


@Composable
internal fun SegmentedButton(
    selectedIndex: ScreenState, options: ImmutableList<ScreenState>, onClick: (ScreenState) -> Unit
) {
    SingleChoiceSegmentedButtonRow {
        options.list.forEachIndexed { index, element ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(index = index, count = options.list.size),
                onClick = { onClick(element) },
                selected = element == selectedIndex
            ) {
                Text(element.title)
            }
        }
    }
}