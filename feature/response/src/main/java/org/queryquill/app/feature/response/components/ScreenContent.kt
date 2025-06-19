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
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.KeyValue
import org.queryquill.app.feature.response.headers.HeadersScreen
import org.queryquill.app.feature.response.model.SegmentedButtonState
import org.queryquill.app.feature.response.preview.PreviewScreen
import org.queryquill.app.feature.response.source.ResponseScreenSource
import org.queryquill.app.feature.response.utils.contentTypeToLanguageType

@Composable
internal fun ScreenContent(
    contentType: ContentType,
    headers: List<KeyValue>,
    segmentedButtonState: SegmentedButtonState,
    updateSegmentedButtonState: (SegmentedButtonState) -> Unit,
    codeEditorState: CodeEditorState,
    fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    codeEditorLoadingState: Boolean,
    fileUri: Uri
) {
    Row {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        ) {
            SegmentedButton(
                currentState = segmentedButtonState, options =
                    listOf(
                        SegmentedButtonState.PREVIEW,
                        SegmentedButtonState.SOURCE,
                        SegmentedButtonState.HEADERS
                    )

            ) { newState ->
                if (newState != segmentedButtonState) {
                    updateSegmentedButtonState(newState)
                }
            }
        }
    }

    when (segmentedButtonState) {
        SegmentedButtonState.PREVIEW -> {
            PreviewScreen(
                contentType = contentType,
                codeEditorState,
                fileLength,
                transferFileToCodeEditorState,
                fileUri,
                codeEditorLoadingState
            )
        }

        SegmentedButtonState.SOURCE -> {
            val languageType = contentTypeToLanguageType(contentType)
            ResponseScreenSource(
                languageType,
                codeEditorState,
                fileLength,
                transferFileToCodeEditorState,
                codeEditorLoadingState
            )
        }

        SegmentedButtonState.HEADERS -> {
            HeadersScreen(headers)
        }
    }
}