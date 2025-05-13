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
import android.os.Build
import androidx.compose.runtime.Composable
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.feature.response.source.ResponseScreenSource


@Composable
internal fun PreviewScreen(
    contentType: ContentType, codeEditorState: CodeEditorState, fileLength: Long,
    transferFileToCodeEditorState: () -> Unit,
    fileUri: Uri,
    codeEditorLoadingState: Boolean,
) {

    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(fileUri)
        ContentType.Image.JPEG -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Application.JSON -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.JSON
            } else {
                LanguageType.OTHER
            }, codeEditorState, fileLength, transferFileToCodeEditorState, codeEditorLoadingState
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            LanguageType.PLAIN,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Image.PNG -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Image.WEBP -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Text.XML -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.XML
            } else {
                LanguageType.OTHER
            }, codeEditorState, fileLength, transferFileToCodeEditorState, codeEditorLoadingState
        )

        ContentType.Image.BMP -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Image.HEIC -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )

        ContentType.Image.HEIF -> Base64ImageDisplay(
            fileUri,
            codeEditorState,
            fileLength,
            transferFileToCodeEditorState,
            codeEditorLoadingState
        )
    }
}