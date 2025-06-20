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

package org.queryquill.app.feature.response.preview

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.feature.response.source.ResponseScreenSource
import org.queryquill.app.feature.response.utils.toLanguageType

@Composable
internal fun PreviewScreen(
    contentType: ContentType, codeEditorState: CodeEditorState, fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    fileUri: Uri,
    codeEditorLoadingState: Boolean,
) {
    when (contentType) {
        ContentType.Text.HTML -> {
            WebViewPage(fileUri)
        }

        is ContentType.Image -> {
            Base64ImageDisplay(
                fileUri,
                codeEditorState,
                fileLength,
                transferFileToCodeEditorState,
                codeEditorLoadingState
            )
        }

        is ContentType.Text, ContentType.Application.JSON -> {
            ResponseScreenSource(
                contentType.toLanguageType(),
                codeEditorState,
                fileLength,
                transferFileToCodeEditorState,
                codeEditorLoadingState
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewPreviewScreen() {
    QueryQuillTheme {
        PreviewScreen(
            contentType = ContentType.Text.PLAIN,
            codeEditorState = CodeEditorState(),
            fileLength = 1000L,
            transferFileToCodeEditorState = {},
            fileUri = Uri.EMPTY,
            codeEditorLoadingState = false
        )
    }
}
