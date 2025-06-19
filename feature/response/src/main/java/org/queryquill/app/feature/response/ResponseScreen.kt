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

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.queryquill.app.feature.response.components.ScreenBar
import org.queryquill.app.feature.response.components.ScreenContent
import org.queryquill.app.feature.response.preview.saveFileLauncher

@Composable
fun ResponseScreen(
    modifier: Modifier
) {
    Column(modifier = modifier) {
        val vm = koinViewModel<ResponseViewModel>()
        val responseModel = vm.responseModel.collectAsStateWithLifecycle().value
        val saveFileLauncher = saveFileLauncher { uri ->
            vm.saveFileLauncher(responseModel.fileName, uri = uri)
        }

        ScreenBar(
            status = responseModel.status,
            time = responseModel.time,
            contentLength = responseModel.contentLength,
            onSaveClick = {
                saveFileLauncher.launch(responseModel.fileName)
            },
            fileName = responseModel.fileName
        )
        if (vm.fileLength != null){
            ScreenContent(
                contentType = responseModel.contentType,
                headers = responseModel.headers,
                segmentedButtonState = vm.segmentedButtonState,
                updateSegmentedButtonState = vm::updateSegmentedButtonState,
                codeEditorState = vm.codeEditorState,
                fileLength = vm.fileLength!!,
                transferFileToCodeEditorState = { vm.transferFileToCodeEditorState(responseModel.fileName) },
                codeEditorLoadingState = vm.codeEditorLoadingState,
                fileUri = vm.getFileUri(responseModel.fileName)
            )
        }
    }
}