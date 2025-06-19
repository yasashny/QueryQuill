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

package org.queryquill.app.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.KeyValue

@Composable
fun KeyValueItem(
    keyValue: KeyValue,
    onTextChanged: (KeyValue) -> Unit,
    modifier: Modifier = Modifier,
    deleteItem: () -> Unit = {},
    cardColors: CardColors = CardDefaults.outlinedCardColors(),
    deleteButtonEnabled: () -> Boolean = { false },
    isDeleteButtonVisible: Boolean = true,
    text1: String = "Name",
    text2: String = "Value"
) {
    OutlinedCard(modifier = modifier, colors = cardColors) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = keyValue.key, onValueChange = {
                    onTextChanged(keyValue.copy(key = it))
                }, label = { Text(text = text1) }, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                if (isDeleteButtonVisible) {
                    FilledTonalIconButton(
                        onClick = { deleteItem() },
                        enabled = deleteButtonEnabled(),
                        modifier = Modifier
                            .padding(start = 15.dp)
                            .testTag("Delete Button")
                    ) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
                    }
                }
            }

            OutlinedTextField(
                value = keyValue.value,
                onValueChange = {
                    onTextChanged(keyValue.copy(value = it))
                },
                label = { Text(text = text2) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            )
        }
    }
}

@Composable
@Preview
private fun KeyValueItemPreview() {
    KeyValueItem(
        keyValue = KeyValue(key = "Sample Key", value = "Sample Value"),
        onTextChanged = {},
        deleteItem = {})
}