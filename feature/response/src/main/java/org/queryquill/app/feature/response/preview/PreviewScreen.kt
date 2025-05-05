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

import android.os.Build
import androidx.compose.runtime.Composable
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ContentType
import org.queryquill.app.core.model.LanguageType
import org.queryquill.app.feature.response.source.ResponseScreenSource
import java.io.File


@Composable
internal fun PreviewScreen(
    contentType: ContentType, file: File, codeEditorState: CodeEditorState
) {

    when (contentType) {
        ContentType.Text.HTML -> WebViewPage(file)
        ContentType.Image.JPEG -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Application.JSON -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.JSON
            } else {
                LanguageType.OTHER
            }, file, codeEditorState
        )

        ContentType.Text.PLAIN -> ResponseScreenSource(
            LanguageType.PLAIN, file, codeEditorState
        )

        ContentType.Image.PNG -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Image.WEBP -> Base64ImageDisplay(file, codeEditorState)

        ContentType.Text.XML -> ResponseScreenSource(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                LanguageType.XML
            } else {
                LanguageType.OTHER
            }, file, codeEditorState
        )

        ContentType.Image.BMP -> Base64ImageDisplay(file, codeEditorState)
        ContentType.Image.HEIC -> Base64ImageDisplay(file, codeEditorState)
        ContentType.Image.HEIF -> Base64ImageDisplay(file, codeEditorState)
    }
}