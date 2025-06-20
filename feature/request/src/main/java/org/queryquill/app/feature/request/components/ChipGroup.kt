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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.InputChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.BodyState


@Composable
internal fun <T : Enum<T>> ChipGroup(
    current: T,
    options: List<T>,
    onSelect: (T) -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    labelMapper: @Composable (T) -> String = { it.name },
) {
    LazyRow(
        state = lazyListState,
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Dimens.small),
        contentPadding = PaddingValues(start = 29.dp, end = Dimens.medium)
    ) {
        items(options, key = { it }) { option ->
            InputChip(
                selected = option == current,
                onClick = { onSelect(option) },
                label = { Text(labelMapper(option)) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ChipGroupPreview() {
    QueryQuillTheme(dynamicColor = false) {
        ChipGroup(
            current = BodyState.Type.NoBody, options = BodyState.Type.entries, onSelect = {})
    }
}