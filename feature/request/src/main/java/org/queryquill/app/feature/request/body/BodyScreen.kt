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

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.core.model.TextType
import org.queryquill.app.feature.request.R
import org.queryquill.app.feature.request.UpdateRequest
import org.queryquill.app.feature.request.components.ChipGroup
import org.queryquill.app.feature.request.components.editableList
import org.queryquill.app.feature.request.dialog.ChangeTypeDialog


internal fun LazyListScope.bodyScreen(
    bodyState: BodyState,
    navigateToEditor: (fileName: String, textType: TextType) -> Unit,
    onBodyEvent: (UpdateRequest.Body) -> Unit
) {
    item("bodyType") {
        var pendingType by remember { mutableStateOf<BodyState.Type?>(null) }
        pendingType?.let { targetType ->
            ChangeTypeDialog(title = stringResource(R.string.body), onDismiss = {
                pendingType = null
            }, onConfirm = {
                onBodyEvent(UpdateRequest.Body.ChangeType(targetType, force = true))
                pendingType = null
            })
        }
        ChipGroup(
            modifier = Modifier
                .padding(vertical = Dimens.small)
                .animateItem(fadeOutSpec = null),
            current = bodyState.type,
            options = BodyState.Type.entries,
            onSelect = { newEnumState: BodyState.Type ->
                onBodyEvent(
                    UpdateRequest.Body.ChangeType(newEnumState) {
                        pendingType = newEnumState
                    })
            })
    }
    when (bodyState) {
        is BodyState.Text -> {
            item("textBody") {
                BodyScreenText(
                    modifier = Modifier.animateItem(fadeOutSpec = null),
                    bodyState = bodyState,
                    updateTextType = {
                        onBodyEvent(UpdateRequest.Body.TextType(it))
                    },
                    navigateToEditor = navigateToEditor
                )
            }
        }

        is BodyState.FormUrlEncoded -> {
            editableList(items = bodyState.list) { updateType, item ->
                onBodyEvent(
                    UpdateRequest.Body.FormUrlEncoded(updateType, item)
                )
            }
        }

        BodyState.NoBody -> {}
        is BodyState.MultipartForm -> {
            bodyScreenMultipartForm(
                items = bodyState.multipart, updateMultipartForm = { updateType, newState ->
                    onBodyEvent(UpdateRequest.Body.MultipartForm(updateType, newState))
                })
        }

        is BodyState.BinaryFile -> {
            item("binaryFile") {
                BodyScreenBinaryFile(
                    modifier = Modifier.animateItem(fadeOutSpec = null),
                    currentState = bodyState,
                    onBodyEvent = onBodyEvent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBodyScreen() {
    val bodyState = BodyState.FormUrlEncoded(
        list = listOf(
            KeyValue("key1", "value1"), KeyValue("key2", "value2")
        )
    )
    QueryQuillTheme {
        LazyColumn {
            bodyScreen(bodyState = bodyState, navigateToEditor = { _, _ -> }, onBodyEvent = {})
        }
    }
}