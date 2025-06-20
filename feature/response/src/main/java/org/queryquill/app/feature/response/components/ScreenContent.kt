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

import android.net.Uri
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.feature.response.headers.HeadersScreen
import org.queryquill.app.feature.response.model.GroupButtonsState
import org.queryquill.app.feature.response.preview.PreviewScreen
import org.queryquill.app.feature.response.source.ResponseScreenSource
import org.queryquill.app.feature.response.utils.toLanguageType

@Composable
internal fun ScreenContent(
    contentType: ContentType,
    headers: List<KeyValue>,
    groupButtonsState: GroupButtonsState,
    onGroupButtonsStateChange: (GroupButtonsState) -> Unit,
    codeEditorState: CodeEditorState,
    fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    isCodeEditorLoading: Boolean,
    fileUri: Uri
) {
    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = Dimens.medium)
        ) {
            GroupButtons(
                currentState = groupButtonsState, options = GroupButtonsState.entries

            ) { newState ->
                if (newState != groupButtonsState) {
                    onGroupButtonsStateChange(newState)
                }
            }
        }
    }
    Crossfade(groupButtonsState) { state ->
        when (state) {
            GroupButtonsState.PREVIEW -> {
                PreviewScreen(
                    contentType = contentType,
                    codeEditorState,
                    fileLength,
                    transferFileToCodeEditorState,
                    fileUri,
                    isCodeEditorLoading
                )
            }

            GroupButtonsState.SOURCE -> {
                ResponseScreenSource(
                    contentType.toLanguageType(),
                    codeEditorState,
                    fileLength,
                    transferFileToCodeEditorState,
                    isCodeEditorLoading
                )
            }

            GroupButtonsState.HEADERS -> {
                HeadersScreen(headers)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewScreenContent() {
    QueryQuillTheme {
        ScreenContent(
            contentType = ContentType.Application.JSON,
            headers = listOf(KeyValue("Content-Type", "application/json")),
            groupButtonsState = GroupButtonsState.HEADERS,
            onGroupButtonsStateChange = {},
            codeEditorState = CodeEditorState(),
            fileLength = 1000L,
            transferFileToCodeEditorState = {},
            isCodeEditorLoading = false,
            fileUri = Uri.EMPTY
        )
    }
}