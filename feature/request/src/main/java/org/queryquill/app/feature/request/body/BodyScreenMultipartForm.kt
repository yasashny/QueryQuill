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

package org.queryquill.app.feature.request.body

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.feature.request.UpdateRequest
import org.queryquill.app.feature.request.components.MultipartFormListItem


internal fun LazyListScope.bodyScreenMultipartForm(
    items: List<MultipartFormState>,
    updateMultipartForm: (updateType: UpdateRequest.UpdateType, newState: MultipartFormState) -> Unit
) {
    itemsIndexed(items, key = { _, item -> item.id }) { index, item ->
        MultipartFormListItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp)
                .animateItem(fadeOutSpec = null),
            multipartFormState = item,
            onItemChange = updateMultipartForm,
            deleteButtonEnabled = { index != items.lastIndex })
        Spacer(modifier = Modifier.height(15.dp))
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewMultipartFormScreen() {
    QueryQuillTheme(dynamicColor = false) {
        val items by remember {
            mutableStateOf(
                listOf(
                    MultipartFormState.Text(KeyValue("name", "Alice")),
                    MultipartFormState.BinaryFile()
                )
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            bodyScreenMultipartForm(items) { _, _ -> }
        }
    }
}