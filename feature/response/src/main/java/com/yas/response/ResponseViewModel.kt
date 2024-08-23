package com.yas.response

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.CodeEditorState
import com.yas.model.ResponseModel
import com.yas.requests.local.TransactionsRepository
import com.yas.response.model.SegmentedButtonState
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import java.net.URI

internal class ResponseViewModel(private val transactionsRepository: TransactionsRepository) :
    ViewModel() {

    val responseModel = transactionsRepository.getCurrentResponseOrNull().map { responseOrNull ->
        responseOrNull ?: ResponseModel.default()
    }.onEach { codeEditorState = CodeEditorState() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), ResponseModel.default())

    fun getFileUriByName(textFileName: String): URI {
        return transactionsRepository.getFileUriByName(textFileName)
    }

    var segmentedButtonState by mutableStateOf(SegmentedButtonState.PREVIEW)
        private set

    fun updateSegmentedButtonState(newState: SegmentedButtonState) {
        segmentedButtonState = newState
    }

    var codeEditorState by mutableStateOf(CodeEditorState())
        private set

}