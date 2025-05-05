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