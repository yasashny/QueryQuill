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
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.ui.KeyValueItem


internal fun LazyListScope.editableList(
    items: List<KeyValue>,
    updateRequest: (items: List<KeyValue>) -> Unit
) {
    itemsIndexed(items) { index, item ->
        KeyValueItem(keyValue = item, onTextChanged = { listItem ->
            val updatedItems = items.toMutableList()
            updatedItems[index] = listItem
            updateRequest(updatedItems)
            if (index == items.size - 1) {
                val newItemList = updatedItems.toMutableList()
                newItemList.add(KeyValue.empty())
                updateRequest(newItemList)
            }
        }, deleteItem = {
            val updatedItems = items.toMutableList()
            updatedItems.removeAt(index)
            updateRequest(updatedItems)
        },
            deleteButtonEnabled = { items.size - 1 != index },
            modifier = Modifier.padding(start = 15.dp, top = 15.dp, end = 15.dp)
        )
    }
}