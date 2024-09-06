package org.queryquill.app.feature.request

import org.queryquill.app.core.model.RequestModel

internal sealed interface RequestUiState {
    data object Loading : RequestUiState
    data class Success(val request: RequestModel) : RequestUiState
}