/*
 * QueryQuill - Api client
 * Copyright (C) 2025 Max Yasashny
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of
 * the GNU General Public License as published by the Free Software Foundation,
 * either version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with this program.
 * If not, see https://www.gnu.org/licenses/.
 */

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