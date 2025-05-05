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

package org.queryquill.app.feature.request.body.multipartForm

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState


internal fun LazyListScope.bodyScreenMultipartForm(
    items: List<MultipartFormState>, updateMultipartForm: (List<MultipartFormState>) -> Unit
) {
    item {
        Spacer(modifier = Modifier.padding(top = 18.dp))
    }
    itemsIndexed(items) { index, item ->
        MultipartFormListItem(multipartFormState = item, onTextChanged = { listItem, flag ->
            val updatedItems = items.toMutableList()
            updatedItems[index] = listItem
            updateMultipartForm(updatedItems)
            if ((index == items.size - 1) and flag) {
                val newItemList = updatedItems.toMutableList()
                newItemList.add(MultipartFormState.Text(KeyValue.empty()))
                updateMultipartForm(newItemList)
            }
        }, deleteItem = {
            val updatedItems = items.toMutableList()
            updatedItems.removeAt(index)
            updateMultipartForm(updatedItems)
        }, deleteButtonEnabled = { items.size - 1 != index })
    }
}