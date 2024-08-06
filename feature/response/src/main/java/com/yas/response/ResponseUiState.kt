package com.yas.response

import com.yas.model.ResponseModel

internal sealed interface ResponseUiState {
    data object Loading : ResponseUiState
    data class Success(val model: ResponseModel) : ResponseUiState
}