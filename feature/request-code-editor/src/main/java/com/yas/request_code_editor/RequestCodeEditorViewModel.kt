package com.yas.request_code_editor

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yas.model.BodyState
import com.yas.requests.local.TransactionsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal class RequestCodeEditorViewModel(private val repository: TransactionsRepository) :
    ViewModel() {

    private val text = mutableStateOf("")

    private val _requestCodeEditorUiState =
        MutableStateFlow<RequestCodeEditorUiState>(RequestCodeEditorUiState.Loading)
    val requestCodeEditorUiState = _requestCodeEditorUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getCurrentRequestOrNull().collect { requestModelOrNull ->
                if (requestModelOrNull == null) {
                    _requestCodeEditorUiState.value = RequestCodeEditorUiState.Loading
                } else {
                    if (requestModelOrNull.bodyState::class == BodyState.Text::class) {
                        text.value = (requestModelOrNull.bodyState as BodyState.Text).text
                        _requestCodeEditorUiState.value = RequestCodeEditorUiState.Success(
                            requestModelOrNull, requestModelOrNull.bodyState as BodyState.Text
                        )
                    } else {
                        _requestCodeEditorUiState.value = RequestCodeEditorUiState.Loading
                    }
                }
            }
        }
    }


    fun updateText(newText: String) {
        text.value = newText
    }

    fun saveBody() {
        viewModelScope.launch(Dispatchers.IO) {
            when (val state = requestCodeEditorUiState.value) {
                RequestCodeEditorUiState.Loading -> {}
                is RequestCodeEditorUiState.Success -> {
                    repository.updateRequest(
                        state.model.copy(
                            bodyState = BodyState.Text(
                                text.value, state.bodyState.textType
                            )
                        )
                    )
                }
            }
        }
    }
}