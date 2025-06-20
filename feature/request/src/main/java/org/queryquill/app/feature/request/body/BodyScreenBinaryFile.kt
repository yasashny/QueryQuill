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

import android.net.Uri
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.queryquill.app.core.designsystem.Dimens
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.BodyState
import org.queryquill.app.core.model.FileInfo
import org.queryquill.app.core.utils.getMIMEType
import org.queryquill.app.feature.request.UpdateRequest
import org.queryquill.app.feature.request.components.BinaryFileElement
import org.queryquill.app.feature.request.dialog.ChangeContentTypeDialog

@Composable
internal fun BodyScreenBinaryFile(
    modifier: Modifier = Modifier,
    currentState: FileInfo,
    onBodyEvent: (UpdateRequest.Body.BinaryFile) -> Unit
) {
    val context = LocalContext.current
    var pendingType by remember { mutableStateOf<String?>(null) }
    pendingType?.let {
        ChangeContentTypeDialog(newContentType = it, onConfirm = {
            onBodyEvent(UpdateRequest.Body.BinaryFile.ChangeContentTypeInHeaders(it))
            pendingType = null
        }, onDismiss = {
            pendingType = null
        })
    }
    BinaryFileElement(
        currentState = currentState, modifier = modifier.padding(horizontal = Dimens.medium)
    ) { uri, fileName ->
        val contentType = getMIMEType(context, uri)
        if (uri != Uri.EMPTY) {
            onBodyEvent(
                UpdateRequest.Body.BinaryFile.File(
                    uri = uri, fileName = fileName, contentType = contentType
                ) {
                    pendingType = contentType
                })
        } else {
            onBodyEvent(
                UpdateRequest.Body.BinaryFile.File(
                    uri = uri, fileName = fileName, contentType = contentType
                )
            )
            onBodyEvent(UpdateRequest.Body.BinaryFile.ChangeContentTypeInHeaders(contentType))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewBinaryFileScreen() {
    val dummyFileInfo = BodyState.BinaryFile(
        uri = Uri.EMPTY,
        fileName = "example.txt",
    )
    QueryQuillTheme {
        BodyScreenBinaryFile(
            currentState = dummyFileInfo, onBodyEvent = {})
    }
}