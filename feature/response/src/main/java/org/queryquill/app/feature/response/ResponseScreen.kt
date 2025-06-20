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

package org.queryquill.app.feature.response

import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.github.rosemoe.sora.text.Content
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.core.designsystem.QueryQuillTheme
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.feature.response.components.ScreenBar
import org.queryquill.app.feature.response.components.ScreenContent
import org.queryquill.app.feature.response.components.saveFileLauncher
import org.queryquill.app.feature.response.model.GroupButtonsState

@Composable
fun ResponseScreen(
    modifier: Modifier
) {
    val vm = koinViewModel<ResponseViewModel>()
    val uiState = vm.responseModel.collectAsStateWithLifecycle().value
    ResponseScreen(
        modifier = modifier,
        uiState = uiState,
        saveFile = vm::saveFile,
        groupButtonsState = vm.groupButtonsState,
        onGroupButtonsStateChange = vm::onGroupButtonsStateChange,
        codeEditorState = vm.codeEditorState,
        transferFileToCodeEditorState = vm::transferFileToCodeEditorState,
        isCodeEditorLoading = vm.isCodeEditorLoading
    )
}

@Composable
internal fun ResponseScreen(
    modifier: Modifier,
    uiState: ResponseUiState,
    saveFile: (fileName: String, uri: Uri) -> Unit,
    groupButtonsState: GroupButtonsState,
    onGroupButtonsStateChange: (GroupButtonsState) -> Unit,
    codeEditorState: CodeEditorState,
    transferFileToCodeEditorState: (fileName: String) -> Unit,
    isCodeEditorLoading: Boolean,
) {
    val saveFileLauncher = saveFileLauncher { uri ->
        saveFile(uiState.model.fileName, uri)
    }
    Column(modifier = modifier) {
        ScreenBar(
            status = uiState.model.status,
            time = uiState.model.time,
            contentLength = uiState.model.contentLength,
            onSaveClick = {
                saveFileLauncher.launch(uiState.model.fileName)
            },
            fileName = uiState.model.fileName
        )
        ScreenContent(
            contentType = uiState.model.contentType,
            headers = uiState.model.headers,
            groupButtonsState = groupButtonsState,
            onGroupButtonsStateChange = onGroupButtonsStateChange,
            codeEditorState = codeEditorState,
            fileLength = uiState.fileLength,
            transferFileToCodeEditorState = { transferFileToCodeEditorState(uiState.model.fileName) },
            isCodeEditorLoading = isCodeEditorLoading,
            fileUri = uiState.fileUri
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewResponseScreen() {
    QueryQuillTheme {
        ResponseScreen(
            modifier = Modifier,
            uiState = ResponseUiState(),
            saveFile = { _, _ -> },
            groupButtonsState = GroupButtonsState.PREVIEW,
            onGroupButtonsStateChange = {},
            codeEditorState = CodeEditorState(initialContent = Content("Hello, World!")),
            transferFileToCodeEditorState = {},
            isCodeEditorLoading = false
        )
    }
}