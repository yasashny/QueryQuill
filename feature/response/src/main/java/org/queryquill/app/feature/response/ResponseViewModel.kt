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
import org.queryquill.app.core.model.CodeEditorState
import org.queryquill.app.core.model.ResponseModel
import org.queryquill.app.core.data.TransactionsRepository
import org.queryquill.app.feature.response.model.SegmentedButtonState

internal class ResponseViewModel(transactionsRepository: TransactionsRepository) : ViewModel() {

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