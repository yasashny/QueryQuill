package com.yas.request

import com.yas.model.RequestModel

internal sealed interface RequestUiState {
    data object Loading : RequestUiState
    data class Success(val request: RequestModel) : RequestUiState
}