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

import android.net.Uri
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.MultipartFormState
import org.queryquill.app.core.model.MultipartFormType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.UpdateRequest

@Composable
internal fun MultipartFormListItem(
    modifier: Modifier = Modifier,
    multipartFormState: MultipartFormState,
    deleteButtonEnabled: () -> Boolean,
    onItemChange: (updateType: UpdateRequest.UpdateType, newState: MultipartFormState) -> Unit
) {
    val options = remember { listOf(MultipartFormType.TEXT, MultipartFormType.FILE) }

    OutlinedCard(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DynamicSelectTextField(
                selectedValue = when (multipartFormState) {
                    is MultipartFormState.Text -> MultipartFormType.TEXT
                    is MultipartFormState.BinaryFile -> MultipartFormType.FILE
                },
                options = options,
                label = stringResource(R.string.type),
                modifier = Modifier.weight(1f)
            ) { newType ->
                val newState = when (newType) {
                    MultipartFormType.TEXT -> MultipartFormState.Text(id = multipartFormState.id)
                    MultipartFormType.FILE -> MultipartFormState.BinaryFile(id = multipartFormState.id)
                }
                onItemChange(UpdateRequest.UpdateType.UPDATE, newState)
            }

            FilledTonalIconButton(
                onClick = {
                    onItemChange(UpdateRequest.UpdateType.DELETE, multipartFormState)
                },
                enabled = deleteButtonEnabled(),
                modifier = Modifier.padding(start = Dimens.medium)
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = null)
            }
        }
        when (multipartFormState) {
            is MultipartFormState.BinaryFile -> BinaryFileContent(
                state = multipartFormState, onItemChange = onItemChange
            )

            is MultipartFormState.Text -> TextContent(
                state = multipartFormState, onItemChange = onItemChange
            )
        }
    }
}

@Composable
private fun BinaryFileContent(
    state: MultipartFormState.BinaryFile,
    onItemChange: (updateType: UpdateRequest.UpdateType, newState: MultipartFormState) -> Unit
) {
    OutlinedTextField(
        value = state.title,
        onValueChange = { onItemChange(UpdateRequest.UpdateType.UPDATE, state.copy(title = it)) },
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.medium)
    )
    BinaryFileElement(
        modifier = Modifier.padding(Dimens.medium), currentState = state
    ) { uri, filename ->
        onItemChange(
            UpdateRequest.UpdateType.UPDATE, state.copy(uri = uri, fileName = filename)
        )
    }
}

@Composable
private fun TextContent(
    state: MultipartFormState.Text,
    onItemChange: (updateType: UpdateRequest.UpdateType, newState: MultipartFormState) -> Unit
) {
    OutlinedTextField(
        value = state.keyValue.key,
        onValueChange = { newKey ->
            onItemChange(
                UpdateRequest.UpdateType.UPDATE,
                state.copy(keyValue = state.keyValue.copy(key = newKey))
            )
        },
        label = { Text(stringResource(R.string.name)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Dimens.medium)
    )

    OutlinedTextField(
        value = state.keyValue.value,
        onValueChange = { newValue ->
            onItemChange(
                UpdateRequest.UpdateType.UPDATE,
                state.copy(keyValue = state.keyValue.copy(value = newValue))
            )
        },
        label = { Text(stringResource(R.string.value)) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.medium)
    )
}

@Preview(name = "List item – Text", showBackground = true)
@Composable
private fun PreviewMultipartFormListItemText() {
    QueryQuillTheme(dynamicColor = false) {
        MultipartFormListItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            multipartFormState = MultipartFormState.Text(KeyValue("key", "value")),
            onItemChange = { _, _ -> },
            deleteButtonEnabled = { true })
    }
}

@Preview(name = "List item – File", showBackground = true)
@Composable
private fun PreviewMultipartFormListItemFile() {
    QueryQuillTheme(dynamicColor = false) {
        MultipartFormListItem(
            Modifier
                .fillMaxWidth()
                .padding(Dimens.medium),
            multipartFormState = MultipartFormState.BinaryFile(
                uri = Uri.EMPTY, title = "Icon", fileName = "icon.png"
            ),
            onItemChange = { _, _ -> },
            deleteButtonEnabled = { true })
    }
}