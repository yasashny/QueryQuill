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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.ui.KeyValueItem
import org.queryquill.app.feature.request.UpdateRequest


internal fun LazyListScope.editableList(
    items: List<KeyValue>,
    updateRequest: (updateType: UpdateRequest.UpdateType, item: KeyValue) -> Unit
) {
    itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
        KeyValueItem(
            keyValue = item,
            onTextChanged = { changed ->
                updateRequest(UpdateRequest.UpdateType.UPDATE, changed)
            },
            deleteItem = {
                updateRequest(UpdateRequest.UpdateType.DELETE, item)
            },
            deleteButtonEnabled = { items.lastIndex != index },
            modifier = Modifier
                .padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
                .animateItem(fadeOutSpec = null)
        )
    }
}

@Preview(showBackground = true)
@Composable
internal fun EditableListPreview() {
    val items = listOf(
        KeyValue("key1", "value1"), KeyValue("key2", "value2"), KeyValue("key3", "value3")
    )
    QueryQuillTheme {
        LazyColumn {
            editableList(items) { _, _ -> }
        }
    }
}