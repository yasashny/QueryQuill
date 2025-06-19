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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.FileRepository
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.feature.response.model.SegmentedButtonState

internal class ResponseViewModel(
    transactionsRepository: TransactionRepository, private val fileRepository: FileRepository
) : ViewModel() {

    val responseModel = transactionsRepository.getCurrentResponseOrNull().map { responseOrNull ->
        responseOrNull ?: ResponseModel.default()
    }.onEach {
        fileLength = null
        viewModelScope.launch {
            fileLength = fileRepository.getFileLength(it.fileName).getOrDefault(0)
        }
        codeEditorState = CodeEditorState()
        codeEditorLoadingState = true
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000L), ResponseModel.default())

    var segmentedButtonState by mutableStateOf(SegmentedButtonState.PREVIEW)

    var fileLength by mutableStateOf<Long?>(null)

    fun updateSegmentedButtonState(newState: SegmentedButtonState) {
        segmentedButtonState = newState
    }

    fun getFileUri(fileName: String): Uri =
        fileRepository.getFileUri(fileName).getOrDefault(Uri.EMPTY)


    fun transferFileToCodeEditorState(fileName: String) {
        viewModelScope.launch {
            val newCodeEditorState = CodeEditorState()
            fileRepository.getChunkedText(fileName).collect {
                val line = codeEditorState.content.lineCount - 1
                val column = codeEditorState.content.getColumnCount(line)
                newCodeEditorState.content.insert(line, column, it)
            }
            codeEditorState = newCodeEditorState
            codeEditorLoadingState = false
        }
    }

    var codeEditorState by mutableStateOf(CodeEditorState())

    var codeEditorLoadingState by mutableStateOf(false)

    fun saveFileLauncher(fileName: String, uri: Uri) {
        viewModelScope.launch {
            fileRepository.saveFileLauncher(fileName, uri)
        }
    }
}