package com.yas.request_code_editor

import com.yas.model.BodyState
import com.yas.model.RequestModel

internal sealed interface RequestCodeEditorUiState {
    data object Loading : RequestCodeEditorUiState
    data class Success(val model: RequestModel, val bodyState: BodyState.Text) :
        RequestCodeEditorUiState
}