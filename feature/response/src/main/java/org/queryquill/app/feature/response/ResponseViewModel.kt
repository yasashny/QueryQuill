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

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import org.queryquill.app.core.data.TransactionRepository
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.feature.response.model.SegmentedButtonState

internal class ResponseViewModel(transactionsRepository: TransactionRepository) : ViewModel() {

    val responseModel = transactionsRepository.getCurrentResponseOrNull().map { responseOrNull ->
        responseOrNull ?: ResponseModel.default()
    }.onEach { codeEditorState = CodeEditorState() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ResponseModel.default())

    var segmentedButtonState by mutableStateOf(SegmentedButtonState.PREVIEW)

    fun updateSegmentedButtonState(newState: SegmentedButtonState) {
        segmentedButtonState = newState
    }

    var codeEditorState by mutableStateOf(CodeEditorState())

}