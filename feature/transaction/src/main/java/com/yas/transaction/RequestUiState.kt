package com.yas.transaction

import com.yas.model.RequestModel

internal sealed interface RequestUiState {
    data object Loading : RequestUiState
    data class Success(val request: RequestModel) : RequestUiState
    data object NewRequest : RequestUiState
}