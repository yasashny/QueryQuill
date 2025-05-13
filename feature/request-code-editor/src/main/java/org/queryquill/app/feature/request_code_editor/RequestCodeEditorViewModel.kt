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

package org.queryquill.app.feature.request_code_editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.queryquill.app.core.data.FileRepository
import org.queryquill.app.core.model.CodeEditorState

internal class RequestCodeEditorViewModel(private val fileRepository: FileRepository) :
    ViewModel() {

    fun transferFileToCodeEditorState(fileName: String) {
        viewModelScope.launch {
            fileRepository.createFileIfNotExist(fileName)
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

    var codeEditorLoadingState by mutableStateOf(true)

    fun saveData(fileName: String) {
        viewModelScope.launch {
            val flow = flow {
                var start = 0
                val end = codeEditorState.content.length
                while (start < end) {
                    emit(
                        codeEditorState.content.substring(
                            start, if (start + 10000 > end) end else start + 10000
                        ).toByteArray()
                    )
                    start += 10000
                }
            }
            fileRepository.saveFileFromFlow(fileName, flow)
        }

    }
}