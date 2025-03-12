package org.queryquill.app.feature.cookie

internal sealed interface CookieUiState {
    data object Loading : CookieUiState
    data class Success(val list: List<CookieModel>) : CookieUiState
}