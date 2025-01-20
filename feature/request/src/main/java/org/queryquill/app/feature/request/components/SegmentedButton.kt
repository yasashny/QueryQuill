package org.queryquill.app.feature.request.components

import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.QueryQuillTheme
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

@Preview
@Composable
private fun PreviewSegmentedButton() {
    QueryQuillTheme {
        SegmentedButton(selectedIndex = ScreenState.BODY, options = ImmutableList(
            listOf(
                ScreenState.BODY, ScreenState.AUTH, ScreenState.HEADER, ScreenState.QUERY
            )
        ), onClick = {})
    }
}